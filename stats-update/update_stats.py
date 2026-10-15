
import argparse
import csv
import io
import os
import sys
from decimal import Decimal, InvalidOperation
from pathlib import Path

import mysql.connector


# --------------------------------------------------
# Configuration
# --------------------------------------------------

BASE_DIR = Path(__file__).resolve().parent

HITTING_FILE = BASE_DIR / "hitting.txt"
PITCHING_FILE = BASE_DIR / "pitching.txt"
SEASON_YEAR = int(os.getenv("SEASON_YEAR", "2026"))

EXPECTED_TEAMS = 30

TEAM_NAME_MAP = {
    "Arizona Diamondbacks": "Arizona Diamondbacks",
    "Athletics": "Athletics",
    "Atlanta Braves": "Atlanta Braves",
    "Baltimore Orioles": "Baltimore Orioles",
    "Boston Red Sox": "Boston Red Sox",
    "Chicago Cubs": "Chicago Cubs",
    "Chicago White Sox": "Chicago White Sox",
    "Cincinnati Reds": "Cincinnati Reds",
    "Cleveland Guardians": "Cleveland Guardians",
    "Colorado Rockies": "Colorado Rockies",
    "Detroit Tigers": "Detroit Tigers",
    "Houston Astros": "Houston Astros",
    "Kansas City Royals": "Kansas City Royals",
    "Los Angeles Angels": "Los Angeles Angels",
    "Los Angeles Dodgers": "Los Angeles Dodgers",
    "Miami Marlins": "Miami Marlins",
    "Milwaukee Brewers": "Milwaukee Brewers",
    "Minnesota Twins": "Minnesota Twins",
    "New York Mets": "New York Mets",
    "New York Yankees": "New York Yankees",
    "Philadelphia Phillies": "Philadelphia Phillies",
    "Pittsburgh Pirates": "Pittsburgh Pirates",
    "San Diego Padres": "San Diego Padres",
    "Seattle Mariners": "Seattle Mariners",
    "San Francisco Giants": "San Francisco Giants",
    "St. Louis Cardinals": "St. Louis Cardinals",
    "Tampa Bay Rays": "Tampa Bay Rays",
    "Texas Rangers": "Texas Rangers",
    "Toronto Blue Jays": "Toronto Blue Jays",
    "Washington Nationals": "Washington Nationals",
}

HITTING_COLUMNS = {
    "Tm", "R", "HR", "BA", "OBP", "SLG", "OPS"
}

PITCHING_COLUMNS = {
    "Tm", "W", "L", "ERA", "R", "SO", "WHIP"
}


# --------------------------------------------------
# Parse Baseball-Reference tables
# --------------------------------------------------

def read_table(filename, required_columns):
    if not filename.exists():
        raise ValueError(f"Input file not found: {filename}")

    content = filename.read_text(encoding="utf-8-sig")

    # Remove blank lines while preserving the tab-separated table.
    content = "\n".join(
        line for line in content.splitlines() if line.strip()
    )

    reader = csv.DictReader(
        io.StringIO(content),
        delimiter="\t"
    )

    if not reader.fieldnames:
        raise ValueError(f"{filename.name} has no header row.")

    # Trim whitespace from header names.
    reader.fieldnames = [
        header.strip() if header else ""
        for header in reader.fieldnames
    ]

    missing = required_columns - set(reader.fieldnames)

    if missing:
        raise ValueError(
            f"{filename.name} is missing columns: "
            f"{', '.join(sorted(missing))}"
        )

    teams = {}

    for line_number, raw_row in enumerate(reader, start=2):
        row = {
            key.strip(): (value or "").strip()
            for key, value in raw_row.items()
            if key is not None
        }

        team = row.get("Tm", "")

        # Ignore non-team summary rows if present.
        if not team or team == "League Average":
            continue

        if team in teams:
            raise ValueError(
                f"{filename.name}, line {line_number}: "
                f"duplicate team {team}"
            )

        teams[team] = row

    if len(teams) != EXPECTED_TEAMS:
        raise ValueError(
            f"{filename.name}: expected {EXPECTED_TEAMS} teams, "
            f"found {len(teams)}."
        )

    return teams


def get_number(row, column, team, number_type):
    value = row.get(column, "").replace(",", "").strip()

    if not value or value in {"-", "—", "N/A"}:
        raise ValueError(
            f"{team}: missing value for column {column}"
        )

    try:
        return number_type(value)
    except (ValueError, InvalidOperation) as exc:
        raise ValueError(
            f"{team}: invalid {column} value: {value}"
        ) from exc


def build_team_stats():
    hitting = read_table(HITTING_FILE, HITTING_COLUMNS)
    pitching = read_table(PITCHING_FILE, PITCHING_COLUMNS)

    if set(hitting) != set(pitching):
        raise ValueError(
            "Hitting and pitching files have different team lists.\n"
            f"Only in hitting: {sorted(set(hitting) - set(pitching))}\n"
            f"Only in pitching: {sorted(set(pitching) - set(hitting))}"
        )

    unmapped = set(hitting) - set(TEAM_NAME_MAP)

    if unmapped:
        raise ValueError(f"Unmapped team names: {sorted(unmapped)}")

    combined = {}

    for source_name in hitting:
        h = hitting[source_name]
        p = pitching[source_name]
        db_name = TEAM_NAME_MAP[source_name]

        combined[db_name] = {
            "wins": get_number(p, "W", source_name, int),
            "losses": get_number(p, "L", source_name, int),
            "runs_scored": get_number(h, "R", source_name, int),
            "runs_allowed": get_number(p, "R", source_name, int),
            "batting_average": get_number(h, "BA", source_name, Decimal),
            "on_base_percentage": get_number(h, "OBP", source_name, Decimal),
            "slugging_percentage": get_number(h, "SLG", source_name, Decimal),
            "earned_run_average": get_number(p, "ERA", source_name, Decimal),
            "ops": get_number(h, "OPS", source_name, Decimal),
            "whip": get_number(p, "WHIP", source_name, Decimal),
            "strikeouts": get_number(p, "SO", source_name, int),
            "home_runs": get_number(h, "HR", source_name, int),
        }

    return combined


# --------------------------------------------------
# Database connection
# --------------------------------------------------

def connect_database():
    required = [
        "DB_HOST",
        "DB_PORT",
        "DB_NAME",
        "DB_USER",
        "DB_PASSWORD",
    ]

    missing = [key for key in required if not os.getenv(key)]

    if missing:
        raise ValueError(
            "Missing environment variables: " + ", ".join(missing)
        )

    return mysql.connector.connect(
        host=os.environ["DB_HOST"],
        port=int(os.environ["DB_PORT"]),
        database=os.environ["DB_NAME"],
        user=os.environ["DB_USER"],
        password=os.environ["DB_PASSWORD"],
        ssl_ca=str(Path(__file__).resolve().parent / "ca.pem"),
        ssl_verify_cert=True,
        ssl_verify_identity=True,
        connection_timeout=15,
    )


# --------------------------------------------------
# Update database
# --------------------------------------------------

def main():
    parser = argparse.ArgumentParser(
        description="Update DiamondIQ team statistics."
    )
    parser.add_argument(
        "--apply",
        action="store_true",
        help="Apply updates. Without this flag, only validate inputs.",
    )
    args = parser.parse_args()

    try:
        stats = build_team_stats()
    except (OSError, ValueError) as exc:
        print(f"VALIDATION FAILED: {exc}", file=sys.stderr)
        return 1

    print(f"Validated {len(stats)} teams.")
    print(f"Target season: {SEASON_YEAR}")

    if not args.apply:
        print("DRY RUN: database has not been changed.")
        print("Run with --apply to write the updates.")
        return 0

    connection = None

    try:
        connection = connect_database()
        cursor = connection.cursor()

        # Check every target record before updating any records.
        planned_updates = []

        for team_name, values in stats.items():
            cursor.execute(
                """
                SELECT t.team_id, COUNT(ts.stat_id)
                FROM teams t
                LEFT JOIN team_statistics ts
                    ON ts.team_id = t.team_id
                    AND ts.season_year = %s
                WHERE t.team_name = %s
                GROUP BY t.team_id
                """,
                (SEASON_YEAR, team_name),
            )

            matches = cursor.fetchall()

            if len(matches) != 1:
                raise ValueError(
                    f"Expected one database team named {team_name}; "
                    f"found {len(matches)}."
                )

            team_id, record_count = matches[0]

            if record_count != 1:
                raise ValueError(
                    f"{team_name}: expected exactly one statistics "
                    f"record for season {SEASON_YEAR}; "
                    f"found {record_count}."
                )

            planned_updates.append(
                (team_id, team_name, values)
            )

        update_sql = """
            UPDATE team_statistics
            SET
                wins = %s,
                losses = %s,
                runs_scored = %s,
                runs_allowed = %s,
                batting_average = %s,
                on_base_percentage = %s,
                slugging_percentage = %s,
                earned_run_average = %s,
                ops = %s,
                whip = %s,
                strikeouts = %s,
                home_runs = %s
            WHERE team_id = %s
              AND season_year = %s
        """

        for team_id, team_name, values in planned_updates:
            cursor.execute(
                update_sql,
                (
                    values["wins"],
                    values["losses"],
                    values["runs_scored"],
                    values["runs_allowed"],
                    values["batting_average"],
                    values["on_base_percentage"],
                    values["slugging_percentage"],
                    values["earned_run_average"],
                    values["ops"],
                    values["whip"],
                    values["strikeouts"],
                    values["home_runs"],
                    team_id,
                    SEASON_YEAR,
                ),
            )

            print(f"Updated: {team_name}")

        connection.commit()
        print(
            f"Successfully updated {len(planned_updates)} teams "
            f"for season {SEASON_YEAR}."
        )

    except Exception as exc:
        if connection is not None:
            connection.rollback()

        print(
            f"UPDATE FAILED — transaction rolled back: {exc}",
            file=sys.stderr,
        )
        return 1

    finally:
        if connection is not None:
            connection.close()

    return 0


if __name__ == "__main__":
    raise SystemExit(main())

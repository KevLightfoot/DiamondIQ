package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Team;

/**
 * Data Access Object (DAO) responsible for retrieving team information
 * from the database.
 *
 * This class provides methods for searching teams and retrieving both
 * basic and detailed team information used throughout the application.
 *
 * @author Kevin Lightfoot
 */
public class TeamDAO {

    /**
     * Searches for teams whose names contain the specified search string.
     *
     * @param search Partial or complete team name entered by the user
     * @return List of matching teams sorted alphabetically
     * @throws SQLException if a database error occurs
     */
    public List<Team> searchTeams(String search) throws SQLException {

        Connection conn = DBConnection.getConnection();

        PreparedStatement searchTeams = conn.prepareStatement(
                "SELECT * FROM teams WHERE team_name LIKE ? ORDER BY team_name");

        searchTeams.setString(1, "%" + search + "%");

        ResultSet rs = searchTeams.executeQuery();

        List<Team> teams = new ArrayList<>();

        // Convert each matching database record into a Team object.
        while (rs.next()) {

            Team team = new Team();

            team.setTeamId(rs.getInt("team_id"));
            team.setTeamName(rs.getString("team_name"));
            team.setAbbreviation(rs.getString("abbreviation"));
            team.setLeague(rs.getString("league"));
            team.setDivision(rs.getString("division"));
            team.setCity(rs.getString("city"));

            teams.add(team);
        }

        rs.close();
        searchTeams.close();
        conn.close();

        return teams;
    }

    /**
     * Retrieves a team's complete information, including season statistics.
     *
     * Team data is joined with the team_statistics table to populate
     * the statistical fields used by the prediction engine.
     *
     * @param teamId Unique identifier of the team
     * @return Fully populated Team object, or null if no matching team exists
     * @throws SQLException if a database error occurs
     */
    public Team getTeam(int teamId) throws SQLException {

        Connection conn = DBConnection.getConnection();

        PreparedStatement grab = conn.prepareStatement(
                "SELECT *\r\n"
                + "FROM teams t\r\n"
                + "JOIN team_statistics ts\r\n"
                + "ON t.team_id = ts.team_id\r\n"
                + "WHERE t.team_id = ?");

        grab.setInt(1, teamId);

        ResultSet rs = grab.executeQuery();

        Team team = null;

        // Populate the Team object if a matching record is found.
        if (rs.next()) {

            team = new Team();

            team.setTeamId(rs.getInt("team_id"));
            team.setTeamName(rs.getString("team_name"));
            team.setAbbreviation(rs.getString("abbreviation"));
            team.setLeague(rs.getString("league"));
            team.setDivision(rs.getString("division"));
            team.setCity(rs.getString("city"));
            team.setWins(rs.getInt("wins"));
            team.setLosses(rs.getInt("losses"));
            team.setRunsScored(rs.getInt("runs_scored"));
            team.setBattingAverage(rs.getDouble("batting_average"));
            team.setOnBasePercentage(rs.getDouble("on_base_percentage"));
            team.setSluggingPercentage(rs.getDouble("slugging_percentage"));
            team.setOps(rs.getDouble("ops"));
            team.setEra(rs.getDouble("earned_run_average"));
            team.setWhip(rs.getDouble("whip"));
            team.setStrikeoutsPerGame(rs.getInt("strikeouts"));
            team.setHomeRuns(rs.getInt("home_runs"));
            team.setRunsAllowed(rs.getInt("runs_allowed"));
        }

        rs.close();
        grab.close();
        conn.close();

        return team;
    }

    /**
     * Retrieves a team using its exact name.
     *
     * @param teamName Exact name of the team
     * @return Matching Team object, or null if no team is found
     * @throws SQLException if a database error occurs
     */
    public Team getTeamByName(String teamName) throws SQLException {

        Connection conn = DBConnection.getConnection();

        PreparedStatement grab = conn.prepareStatement(
                "SELECT * FROM teams WHERE team_name = ?");

        grab.setString(1, teamName);

        ResultSet rs = grab.executeQuery();

        Team team = null;

        // Create a Team object if a matching record exists.
        if (rs.next()) {

            team = new Team();

            team.setTeamId(rs.getInt("team_id"));
            team.setTeamName(rs.getString("team_name"));
            team.setAbbreviation(rs.getString("abbreviation"));
            team.setLeague(rs.getString("league"));
            team.setDivision(rs.getString("division"));
            team.setCity(rs.getString("city"));
        }

        rs.close();
        grab.close();
        conn.close();

        return team;
    }

    /**
     * Retrieves a team's basic identifying information.
     *
     * Unlike getTeam(), this method only loads general team information
     * and does not retrieve season statistics. It is intended for
     * situations where only basic team details are required.
     *
     * @param teamId Unique identifier of the team
     * @return Team object containing basic information, or null if no team exists
     * @throws SQLException if a database error occurs
     */
    public Team getTeamBasic(int teamId) throws SQLException {

        Connection conn = DBConnection.getConnection();

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM teams WHERE team_id = ?");

        stmt.setInt(1, teamId);

        ResultSet rs = stmt.executeQuery();

        Team team = null;

        // Populate only the team's identifying information.
        if (rs.next()) {

            team = new Team();

            team.setTeamId(rs.getInt("team_id"));
            team.setTeamName(rs.getString("team_name"));
            team.setAbbreviation(rs.getString("abbreviation"));
            team.setCity(rs.getString("city"));
        }

        rs.close();
        stmt.close();
        conn.close();

        return team;
    }

}
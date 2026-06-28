package model;

/**
 * Represents a Major League Baseball team and its associated
 * statistical information used by the DiamondIQ prediction engine.
 *
 * This model stores both identifying team information and season
 * performance statistics retrieved from the database.
 *
 * @author Kevin Lightfoot
 */
public class Team {

    /** Unique identifier for the team. */
    private int teamId;

    /** Official team name. */
    private String teamName;

    /** Standard three-letter team abbreviation. */
    private String abbreviation;

    /** League in which the team competes (American or National). */
    private String league;

    /** Division within the league. */
    private String division;

    /** Home city of the team. */
    private String city;

    /** Total number of wins during the season. */
    private Integer wins;

    /** Total number of losses during the season. */
    private Integer losses;

    /** Total runs scored by the team. */
    private Integer runsScored;

    /** Team batting average. */
    private Double battingAverage;

    /** Team on-base percentage (OBP). */
    private Double onBasePercentage;

    /** Team slugging percentage (SLG). */
    private Double sluggingPercentage;

    /** Team on-base plus slugging (OPS). */
    private Double ops;

    /** Team earned run average (ERA). */
    private Double era;

    /** Team walks plus hits per inning pitched (WHIP). */
    private Double whip;

    /** Total strikeouts recorded by the pitching staff. */
    private Integer strikeoutsPerGame;

    /** Total home runs hit by the team. */
    private Integer homeRuns;

    /** Total runs allowed by the pitching staff. */
    private Integer runsAllowed;

    /**
     * Creates an empty Team object.
     */
    public Team() {

    }

    /**
     * Creates a Team object with its basic identifying information.
     *
     * @param teamId Unique team identifier
     * @param teamName Official team name
     * @param abbreviation Team abbreviation
     * @param league League affiliation
     * @param division Division affiliation
     * @param city Home city
     */
    public Team(int teamId, String teamName, String abbreviation,
                String league, String division, String city) {

        this.teamId = teamId;
        this.teamName = teamName;
        this.abbreviation = abbreviation;
        this.league = league;
        this.division = division;
        this.city = city;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(Integer runsScored) {
        this.runsScored = runsScored;
    }

    public Double getBattingAverage() {
        return battingAverage;
    }

    public void setBattingAverage(Double battingAverage) {
        this.battingAverage = battingAverage;
    }

    public Double getOnBasePercentage() {
        return onBasePercentage;
    }

    public void setOnBasePercentage(Double onBasePercentage) {
        this.onBasePercentage = onBasePercentage;
    }

    public Double getSluggingPercentage() {
        return sluggingPercentage;
    }

    public void setSluggingPercentage(Double sluggingPercentage) {
        this.sluggingPercentage = sluggingPercentage;
    }

    public Double getOps() {
        return ops;
    }

    public void setOps(Double ops) {
        this.ops = ops;
    }

    public Double getEra() {
        return era;
    }

    public void setEra(Double era) {
        this.era = era;
    }

    public Double getWhip() {
        return whip;
    }

    public void setWhip(Double whip) {
        this.whip = whip;
    }

    public Integer getStrikeoutsPerGame() {
        return strikeoutsPerGame;
    }

    public void setStrikeoutsPerGame(Integer strikeoutsPerGame) {
        this.strikeoutsPerGame = strikeoutsPerGame;
    }

    public Integer getHomeRuns() {
        return homeRuns;
    }

    public void setHomeRuns(Integer homeRuns) {
        this.homeRuns = homeRuns;
    }

    public Integer getRunsAllowed() {
        return runsAllowed;
    }

    public void setRunsAllowed(Integer runsAllowed) {
        this.runsAllowed = runsAllowed;
    }

    /**
     * Returns the relative file path to the team's logo image.
     * If no team abbreviation is available, a default baseball image
     * is returned instead.
     *
     * @return Relative path to the appropriate team logo image
     */
    public String getLogoPath() {

        // Return a default image when no team abbreviation is available.
        if (abbreviation == null) {

            return "images/logos/baseball.jpg";

        }

        // Build the logo filename using the team's abbreviation.
        return "images/logos/" +
                abbreviation.toLowerCase() +
                ".png";

    }
}
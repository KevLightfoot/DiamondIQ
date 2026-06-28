package dao;

import database.DBConnection;
import model.Matchup;
import model.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) responsible for managing matchup prediction
 * history within the database.
 *
 * This class provides methods for saving completed predictions and
 * retrieving matchup history for display throughout the application.
 *
 * @author Kevin Lightfoot
 */
public class MatchupDAO {

    /**
     * Saves a completed matchup prediction to the database.
     *
     * @param userId        ID of the user who generated the prediction
     * @param teamOne       First team in the matchup
     * @param teamTwo       Second team in the matchup
     * @param winner        Predicted winning team
     * @param reasoning     AI-generated prediction analysis
     * @param teamOneScore  Projected score for Team One
     * @param teamTwoScore  Projected score for Team Two
     * @param confidence    Confidence level assigned to the prediction
     * @throws SQLException if a database error occurs
     */
    public void savePrediction(
            int userId,
            Team teamOne,
            Team teamTwo,
            Team winner,
            String reasoning,
            double teamOneScore,
            double teamTwoScore,
            int confidence) throws SQLException {

        Connection conn = DBConnection.getConnection();

        // Insert the completed prediction into the user's matchup history.
        PreparedStatement stmt = conn.prepareStatement(

            "INSERT INTO matchup_history " +
            "(user_id, team_one_id, team_two_id, predicted_winner_id, analysis_summary, " +
            "team_one_score, team_two_score, confidence) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        stmt.setInt(1, userId);
        stmt.setInt(2, teamOne.getTeamId());
        stmt.setInt(3, teamTwo.getTeamId());
        stmt.setInt(4, winner.getTeamId());
        stmt.setString(5, reasoning);
        stmt.setDouble(6, teamOneScore);
        stmt.setDouble(7, teamTwoScore);
        stmt.setInt(8, confidence);

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    /**
     * Retrieves all saved matchup predictions for a specific user.
     * Results are ordered from newest to oldest based on creation date.
     *
     * @param userId ID of the authenticated user
     * @return List containing the user's matchup history
     * @throws SQLException if a database error occurs
     */
    public List<Matchup> getHistory(int userId) throws SQLException {

        Connection conn = DBConnection.getConnection();

        PreparedStatement stmt = conn.prepareStatement(

            "SELECT mh.*, " +

            "t1.team_name AS team1_name, " +

            "t2.team_name AS team2_name, " +

            "pw.team_name AS winner_name " +

            "FROM matchup_history mh " +

            "JOIN teams t1 ON mh.team_one_id=t1.team_id " +

            "JOIN teams t2 ON mh.team_two_id=t2.team_id " +

            "JOIN teams pw ON mh.predicted_winner_id=pw.team_id " +

            "WHERE user_id=? " +

            "ORDER BY created_at DESC");

        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();

        List<Matchup> history = new ArrayList<>();

        // Convert each database record into a Matchup object.
        while (rs.next()) {

            Matchup matchup = new Matchup();

            matchup.setMatchupId(
                    rs.getInt("matchup_id"));

            // Populate lightweight Team objects for display purposes.
            Team team1 = new Team();
            team1.setTeamName(rs.getString("team1_name"));

            Team team2 = new Team();
            team2.setTeamName(rs.getString("team2_name"));

            Team winner = new Team();
            winner.setTeamName(rs.getString("winner_name"));

            matchup.setTeamOne(team1);
            matchup.setTeamTwo(team2);
            matchup.setPredictedWinner(winner);

            matchup.setAnalysisSummary(rs.getString("analysis_summary"));
            matchup.setCreatedAt(rs.getTimestamp("created_at"));

            history.add(matchup);
        }

        rs.close();
        stmt.close();
        conn.close();

        return history;
    }

    /**
     * Retrieves the complete details for a single saved matchup prediction.
     *
     * This method is used when viewing an individual prediction from
     * the matchup history page.
     *
     * @param matchupId Unique identifier of the matchup
     * @return Fully populated Matchup object, or null if no record exists
     * @throws SQLException if a database error occurs
     */
    public Matchup getMatchup(int matchupId) throws SQLException {

        Connection conn = DBConnection.getConnection();

        PreparedStatement stmt = conn.prepareStatement(

            "SELECT mh.*, " +

            "t1.team_id AS team1_id, " +
            "t1.team_name AS team1_name, " +
            "t1.city AS team1_city, " +
            "t1.abbreviation AS team1_abbreviation, " +

            "t2.team_id AS team2_id, " +
            "t2.team_name AS team2_name, " +
            "t2.city AS team2_city, " +
            "t2.abbreviation AS team2_abbreviation, " +

            "pw.team_id AS winner_id, " +
            "pw.team_name AS winner_name, " +
            "pw.city AS winner_city, " +
            "pw.abbreviation AS winner_abbreviation " +

            "FROM matchup_history mh " +

            "JOIN teams t1 ON mh.team_one_id=t1.team_id " +

            "JOIN teams t2 ON mh.team_two_id=t2.team_id " +

            "JOIN teams pw ON mh.predicted_winner_id=pw.team_id " +

            "WHERE matchup_id=?");

        stmt.setInt(1, matchupId);

        ResultSet rs = stmt.executeQuery();

        Matchup matchup = null;

        // Populate the Matchup object if a matching record is found.
        if (rs.next()) {

            matchup = new Matchup();

            matchup.setMatchupId(rs.getInt("matchup_id"));

            // Build Team objects using the associated database information.
            Team team1 = new Team();
            team1.setTeamId(rs.getInt("team1_id"));
            team1.setTeamName(rs.getString("team1_name"));
            team1.setCity(rs.getString("team1_city"));
            team1.setAbbreviation(rs.getString("team1_abbreviation"));

            Team team2 = new Team();
            team2.setTeamId(rs.getInt("team2_id"));
            team2.setTeamName(rs.getString("team2_name"));
            team2.setCity(rs.getString("team2_city"));
            team2.setAbbreviation(rs.getString("team2_abbreviation"));

            Team winner = new Team();
            winner.setTeamId(rs.getInt("winner_id"));
            winner.setTeamName(rs.getString("winner_name"));
            winner.setCity(rs.getString("winner_city"));
            winner.setAbbreviation(rs.getString("winner_abbreviation"));

            matchup.setTeamOne(team1);
            matchup.setTeamTwo(team2);
            matchup.setPredictedWinner(winner);

            matchup.setAnalysisSummary(
                    rs.getString("analysis_summary"));

            matchup.setCreatedAt(
                    rs.getTimestamp("created_at"));
        }

        rs.close();
        stmt.close();
        conn.close();

        return matchup;
    }

}
package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

/**
 * Data Access Object (DAO) responsible for managing user-related
 * database operations.
 *
 * This class provides methods for user registration, authentication,
 * profile retrieval, and account preference management.
 *
 * @author Kevin Lightfoot
 */
public class UserDAO {

    /**
     * Determines whether a username already exists in the database.
     *
     * @param username Username to check for uniqueness
     * @return true if the username already exists; otherwise false
     * @throws SQLException if a database error occurs
     */
    public boolean usernameExists(String username) throws SQLException {

        Connection conn = DBConnection.getConnection();

        // Search for an existing user with the specified username.
        PreparedStatement matchUsername = conn.prepareStatement(
                "SELECT username FROM users WHERE username = ?");

        matchUsername.setString(1, username);

        ResultSet rs = matchUsername.executeQuery();

        boolean dupe = rs.next();

        rs.close();
        matchUsername.close();
        conn.close();

        return dupe;
    }

    /**
     * Creates a new user account in the database.
     *
     * @param username User's chosen username
     * @param email User's email address
     * @param password User's account password
     * @throws SQLException if a database error occurs
     */
    public void createUser(String username, String email, String password) throws SQLException {

        Connection conn = DBConnection.getConnection();

        // Insert the newly registered user into the users table.
        PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO users (username, email, password)"
                + " VALUES (?, ?, ?);");

        insert.setString(1, username);
        insert.setString(2, email);
        insert.setString(3, password);

        insert.executeUpdate();

        insert.close();
        conn.close();
    }

    /**
     * Validates a user's login credentials.
     *
     * @param username Username entered during login
     * @param password Password entered during login
     * @return true if the credentials match a registered user; otherwise false
     * @throws SQLException if a database error occurs
     */
    public boolean validateLogin(String username, String password) throws SQLException {

        Connection conn = DBConnection.getConnection();

        // Verify that both the username and password match a stored account.
        PreparedStatement matchUser = conn.prepareStatement(
                "SELECT username FROM users WHERE username = ? AND password = ?");

        matchUser.setString(1, username);
        matchUser.setString(2, password);

        ResultSet rs = matchUser.executeQuery();

        boolean valid = rs.next();

        rs.close();
        matchUser.close();
        conn.close();

        return valid;
    }

    /**
     * Updates the user's favorite team preference.
     *
     * @param username Username of the account to update
     * @param teamId ID of the selected favorite team
     * @throws SQLException if a database error occurs
     */
    public void setFavoriteTeam(String username, int teamId) throws SQLException {

        Connection conn = DBConnection.getConnection();

        // Store the selected favorite team for the specified user.
        PreparedStatement updateFavorite = conn.prepareStatement(
                "UPDATE users SET favorite_team_id = ? WHERE username = ?");

        updateFavorite.setInt(1, teamId);
        updateFavorite.setString(2, username);

        updateFavorite.executeUpdate();

        updateFavorite.close();
        conn.close();
    }

    /**
     * Retrieves the unique identifier associated with a user account.
     *
     * @param username Username of the requested user
     * @return User ID if found; otherwise -1
     * @throws SQLException if a database error occurs
     */
    public int getUserId(String username) throws SQLException {

        Connection conn = DBConnection.getConnection();

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT user_id FROM users WHERE username = ?");

        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        int userId = -1;

        // Retrieve the user's unique identifier if a matching account exists.
        if (rs.next()) {
            userId = rs.getInt("user_id");
        }

        rs.close();
        stmt.close();
        conn.close();

        return userId;
    }

    /**
     * Retrieves a user's account information.
     *
     * @param username Username of the requested account
     * @return Fully populated User object, or null if no matching user exists
     * @throws SQLException if a database error occurs
     */
    public User getUser(String username) throws SQLException {

        Connection conn = DBConnection.getConnection();

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE username = ?");

        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        User user = null;

        // Populate the User object if a matching account is found.
        if (rs.next()) {

            user = new User();

            user.setUserId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setFavoriteTeamId((Integer) rs.getObject("favorite_team_id"));
        }

        rs.close();
        stmt.close();
        conn.close();

        return user;
    }
}
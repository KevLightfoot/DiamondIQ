package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a centralized method for establishing connections to the
 * DiamondIQ MySQL database.
 *
 * This utility class stores the database connection credentials and
 * returns a new database connection whenever requested by the application's
 * Data Access Objects (DAOs).
 *
 * @author Kevin Lightfoot
 */
public class DBConnection {

    /** Database connection URL. */
    private static final String URL = "jdbc:mysql://localhost:3306/diamondiq";

    /** Database username. */
    private static final String USER = "diamondiq";

    /** Database password. */
    private static final String PASSWORD = "DiamondIQ2026!";

    /**
     * Creates and returns a new connection to the DiamondIQ database.
     *
     * @return Active database connection
     * @throws SQLException if a connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
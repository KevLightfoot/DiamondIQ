
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String HOST = System.getenv("DB_HOST");
    private static final String PORT = System.getenv().getOrDefault("DB_PORT", "4000");
    private static final String DATABASE = System.getenv().getOrDefault("DB_NAME", "diamondiq");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        if (HOST == null || HOST.isBlank()
                || USER == null || USER.isBlank()
                || PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException(
                "Database configuration missing. Set DB_HOST, DB_USER, and DB_PASSWORD."
            );
        }

        String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
                + "?sslMode=VERIFY_IDENTITY";

        return DriverManager.getConnection(url, USER, PASSWORD);
    }
}

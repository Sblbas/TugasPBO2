package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class DatabaseHelper {
        private static final String DB_URL = "jdbc:sqlite:vila.db";

        public static Connection connect() throws SQLException {
            return DriverManager.getConnection(DB_URL);
        }
    }


    



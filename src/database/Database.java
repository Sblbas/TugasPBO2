package src.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:data/villa.db";

    // Menambahkan metode untuk memeriksa koneksi
    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            System.out.println("Koneksi ke database berhasil.");
            return connection;
        } catch (SQLException e) {
            System.err.println("Gagal terhubung ke database: " + e.getMessage());
            throw e;
        }
    }
}
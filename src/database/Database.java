package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    public static Connection connect() {
        try {
            String url = "jdbc:sqlite:data/villadb.db";
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            System.out.println("❌ Gagal koneksi ke database: " + e.getMessage());
            return null;
        }
    }

    public static void init() {
        initCustomerTable();
        // Jika kamu punya init untuk tabel lain, panggil juga di sini
    }

    // ✅ Tambahan: Membuat tabel customer jika belum ada
    public static void initCustomerTable() {
        String sql = "CREATE TABLE IF NOT EXISTS customer ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "email TEXT NOT NULL,"
                + "phone TEXT"
                + ");";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Tabel customer siap digunakan.");
        } catch (SQLException e) {
            System.out.println("❌ Gagal membuat tabel customer: " + e.getMessage());
        }
    }
}

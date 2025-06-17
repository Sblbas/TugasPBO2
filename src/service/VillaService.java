package src.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import database.Database;
import model.Villa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VillaService {

    public String getAllVillasAsJson() throws Exception {
        List<Villa> villas = new ArrayList<>();

        String sql = "SELECT id, name, address, price, capacity FROM villas";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Villa villa = new Villa();
                villa.setId(rs.getInt("id"));
                villa.setName(rs.getString("name"));
                villa.setAddress(rs.getString("address"));
                villa.setPrice(rs.getDouble("price"));
                villa.setCapacity(rs.getInt("capacity"));
                villas.add(villa);
            }

        } catch (Exception e) {
            throw new Exception("Gagal mengambil data villa: " + e.getMessage());
        }

        // Menggunakan Jackson untuk mengubah list menjadi JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Untuk membuat JSON lebih mudah dibaca
        return objectMapper.writeValueAsString(villas);
    }
}

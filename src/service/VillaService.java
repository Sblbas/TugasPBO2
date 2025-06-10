package src.service;

import src.model.Villa;
import src.database.Database;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VillaService {

    public String getAllVillasAsJson() throws Exception {
        List<Villa> villas = new ArrayList<>();

        String sql = "SELECT id, name, location, price, capacity FROM villas";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Villa villa = new Villa();
                villa.setId(rs.getInt("id"));
                villa.setName(rs.getString("name"));
                villa.setLocation(rs.getString("location"));
                villa.setPrice(rs.getDouble("price"));
                villa.setCapacity(rs.getInt("capacity"));
                villas.add(villa);
            }

        } catch (Exception e) {
            throw new Exception("Gagal mengambil data villa: " + e.getMessage());
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(villas);
    }
}

package service;

import database.DatabaseHelper;
import model.Villa;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VillaService {
    public static String getAllVillas() {
        List<Villa> villas = new ArrayList<>();
        String sql = "SELECT * FROM villas";

        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                villas.add(new Villa(rs.getInt("id"), rs.getString("name"), rs.getString("location")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSONArray json = new JSONArray();
        for (Villa v : villas) {
            JSONObject obj = new JSONObject();
            obj.put("id", v.getId());
            obj.put("name", v.getName());
            obj.put("location", v.getLocation());
            json.put(obj);
        }

        return json.toString();
    }
}

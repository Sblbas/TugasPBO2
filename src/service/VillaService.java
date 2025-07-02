package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import model.Villa;
import model.Room;
import database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import com.sun.net.httpserver.HttpExchange;

public class VillaService {
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Villa> getAllVillas() {
        List<Villa> villas = new ArrayList<>();
        String query = "SELECT * FROM villas";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Villa villa = new Villa(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("address")
                );
                villas.add(villa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return villas;
    }

    public String getAllVillasAsJson() {
        try {
            return mapper.writeValueAsString(getAllVillas());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    public String getVillaByIdAsJson(int id) {
        Villa villa = null;
        String query = "SELECT * FROM villas WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                villa = new Villa(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            return villa != null ? mapper.writeValueAsString(villa) : "{}";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public String createVilla(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        Villa villa = mapper.readValue(is, Villa.class);

        String query = "INSERT INTO villas (name, description, address) VALUES (?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, villa.getName());
            stmt.setString(2, villa.getDescription());
            stmt.setString(3, villa.getAddress());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                villa.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mapper.writeValueAsString(villa);
    }

    public String updateVilla(HttpExchange exchange, int id) throws IOException {
        InputStream is = exchange.getRequestBody();
        Villa villa = mapper.readValue(is, Villa.class);

        String query = "UPDATE villas SET name = ?, description = ?, address = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, villa.getName());
            stmt.setString(2, villa.getDescription());
            stmt.setString(3, villa.getAddress());
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        villa.setId(id);
        return mapper.writeValueAsString(villa);
    }

    public String deleteVilla(int id) {
        String query = "DELETE FROM villas WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"error\":\"Deletion failed\"}";
        }
        return "{\"message\":\"Villa deleted successfully\"}";
    }

    public String searchVillaByDateAsJson(String queryString) {
        // Belum diimplementasikan: parsing query ci_date & co_date
        return "[]";
    }

    public String getRoomsByVillaIdAsJson(int villaId) {
        // Placeholder - tergantung model Room
        return "[]";
    }

    public String getBookingsByVillaIdAsJson(int villaId) {
        return "[]";
    }

    public String getReviewsByVillaIdAsJson(int villaId) {
        return "[]";
    }

//    public String addRoomToVilla(HttpExchange exchange, int villaId) throws IOException {
//        // Placeholder - tergantung struktur tabel rooms
//        InputStream is = exchange.getRequestBody();
//        Room room = mapper.readValue(is, Room.class);
//        room.setVillaId(villaId);
//
//        String query = "INSERT INTO rooms (villa_id, name, price, facilities) VALUES (?, ?, ?, ?)";
//
//        try (Connection conn = Database.connect();
//             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setInt(1, room.getVillaId());
//            stmt.setString(2, room.getName());
//            stmt.setDouble(3, room.getPrice());
//            stmt.setString(4, room.getFacilities());
//            stmt.executeUpdate();
//
//            ResultSet keys = stmt.getGeneratedKeys();
//            if (keys.next()) {
//                room.setId(keys.getInt(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return mapper.writeValueAsString(room);
//    }

//    public String updateRoom(HttpExchange exchange, int villaId, int roomId) throws IOException {
//        InputStream is = exchange.getRequestBody();
//        Room room = mapper.readValue(is, Room.class);
//
//        String query = "UPDATE rooms SET name = ?, price = ?, facilities = ? WHERE id = ? AND villa_id = ?";
//
//        try (Connection conn = Database.connect();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, room.getName());
//            stmt.setDouble(2, room.getPrice());
//            stmt.setString(3, room.getFacilities());
//            stmt.setInt(4, roomId);
//            stmt.setInt(5, villaId);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        room.setId(roomId);
//        room.setVillaId(villaId);
//        return mapper.writeValueAsString(room);
//    }

    public String deleteRoomFromVilla(int villaId, int roomId) {
        String query = "DELETE FROM rooms WHERE id = ? AND villa_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            stmt.setInt(2, villaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"error\":\"Room deletion failed\"}";
        }

        return "{\"message\":\"Room deleted successfully\"}";
    }
}
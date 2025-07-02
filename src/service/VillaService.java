package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import database.RoomDAO;
import model.Villa;
import model.Room;
import database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import com.sun.net.httpserver.HttpExchange;

public class VillaService {
    private RoomDAO roomDAO = new RoomDAO(); // atau lewat constructor
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

    public List<Room> getRoomsByVillaId(int villaId) {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM room_types WHERE villa_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, villaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room();
                    room.setId(rs.getInt("id"));
                    room.setVilla(rs.getInt("villa_id"));
                    room.setName(rs.getString("name"));
                    room.setQuantity(rs.getInt("quantity"));
                    room.setCapacity(rs.getInt("capacity"));
                    room.setPrice(rs.getInt("price"));
                    room.setBedSize(Room.BedSize.valueOf(rs.getString("bed_size").toUpperCase()));
                    room.setHasDesk(rs.getBoolean("hasDesk"));
                    room.setHasAc(rs.getBoolean("hasAc"));
                    room.setHasTv(rs.getBoolean("hasTv"));
                    room.setHasWifi(rs.getBoolean("hasWifi"));
                    room.setHasShower(rs.getBoolean("hasShower"));
                    room.setHasHotwater(rs.getBoolean("hasHotwater"));
                    room.setHasFridge(rs.getBoolean("hasFridge"));

                    rooms.add(room);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Atau log error
        }


        return rooms;
    }

    public String getRoomsByVillaIdAsJson(int villaId) {
        List<Room> rooms = roomDAO.getRoomsByVillaId(villaId);
        try {
            return mapper.writeValueAsString(rooms); // Jackson ObjectMapper
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"error\": \"JSON serialization failed\"}";
        }
    }

    public String getBookingsByVillaIdAsJson(int villaId) {
        return "[]";
    }

    public String getReviewsByVillaIdAsJson(int villaId) {
        return "[]";
    }

    public String addRoomToVilla(HttpExchange exchange, int villaId) throws IOException {
        InputStream is = exchange.getRequestBody();
        Room room = mapper.readValue(is, Room.class);
        room.setVilla(villaId);  // ganti dari setVillaId

        String query = "INSERT INTO room_types (villa, name, quantity, capacity, price, bed_size, has_desk, has_ac, has_tv, has_wifi, has_shower, has_hotwater, has_fridge) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";  // ⬅️ Total: 14 kolom


        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, room.getVilla());
            stmt.setString(2, room.getName());
            stmt.setInt(3, room.getQuantity());
            stmt.setInt(4, room.getCapacity());
            stmt.setInt(5, room.getPrice());
            stmt.setString(6, room.getBedSize().toString().toLowerCase());
            stmt.setBoolean(7, room.hasDesk());
            stmt.setBoolean(8, room.hasAc());
            stmt.setBoolean(9, room.hasTv());
            stmt.setBoolean(10, room.hasWifi());
            stmt.setBoolean(11, room.hasShower());
            stmt.setBoolean(12, room.hasHotwater());
            stmt.setBoolean(13, room.hasFridge());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                room.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mapper.writeValueAsString(room);
    }

    public String updateRoom(HttpExchange exchange, int villaId, int roomId) throws IOException {
        InputStream is = exchange.getRequestBody();
        Room room = mapper.readValue(is, Room.class);
        room.setId(roomId);
        room.setVilla(villaId);

        String query = "UPDATE room_types SET name = ?, quantity = ?, capacity = ?, price = ?, bed_size = ?, " +
                "has_desk = ?, has_ac = ?, has_tv = ?, has_wifi = ?, has_shower = ?, has_hotwater = ?, has_fridge = ? " +
                "WHERE id = ? AND villa = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, room.getName());
            stmt.setInt(2, room.getQuantity());
            stmt.setInt(3, room.getCapacity());
            stmt.setInt(4, room.getPrice());
            stmt.setString(5, room.getBedSize().toString());
            stmt.setBoolean(6, room.hasDesk());
            stmt.setBoolean(7, room.hasAc());
            stmt.setBoolean(8, room.hasTv());
            stmt.setBoolean(9, room.hasWifi());
            stmt.setBoolean(10, room.hasShower());
            stmt.setBoolean(11, room.hasHotwater());
            stmt.setBoolean(12, room.hasFridge());
            stmt.setInt(13, roomId);
            stmt.setInt(14, villaId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mapper.writeValueAsString(room);
    }

    public String deleteRoomFromVilla(int villaId, int roomId) {
        String query = "DELETE FROM room_types WHERE id = ? AND villa = ?";

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
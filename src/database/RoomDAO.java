package database;

import model.Room;
import model.Room.BedSize;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public List<Room> getRoomsByVillaId(int villaId) {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM room_types WHERE villa = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, villaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setVilla(rs.getInt("villa"));
                room.setName(rs.getString("name"));
                room.setQuantity(rs.getInt("quantity"));
                room.setCapacity(rs.getInt("capacity"));
                room.setPrice(rs.getInt("price"));
                room.setBedSize(BedSize.valueOf(rs.getString("bed_size").toUpperCase()));
                room.setHasDesk(rs.getBoolean("has_desk"));
                room.setHasAc(rs.getBoolean("has_ac"));
                room.setHasTv(rs.getBoolean("has_tv"));
                room.setHasWifi(rs.getBoolean("has_wifi"));
                room.setHasShower(rs.getBoolean("has_shower"));
                room.setHasHotwater(rs.getBoolean("has_hotwater"));
                room.setHasFridge(rs.getBoolean("has_fridge"));

                rooms.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    public Room insertRoom(Room room) {
        String query = "INSERT INTO room_types (villa, name, quantity, capacity, price, bed_size, " +
                "has_desk, has_ac, has_tv, has_wifi, has_shower, has_hotwater, has_fridge) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

        return room;
    }

    public boolean updateRoom(Room room) {
        String query = "UPDATE room_types SET name = ?, quantity = ?, capacity = ?, price = ?, bed_size = ?, " +
                "has_desk = ?, has_ac = ?, has_tv = ?, has_wifi = ?, has_shower = ?, has_hotwater = ?, has_fridge = ? " +
                "WHERE id = ? AND villa = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, room.getName());
            stmt.setInt(2, room.getQuantity());
            stmt.setInt(3, room.getCapacity());
            stmt.setInt(4, room.getPrice());
            stmt.setString(5, room.getBedSize().toString().toLowerCase());
            stmt.setBoolean(6, room.hasDesk());
            stmt.setBoolean(7, room.hasAc());
            stmt.setBoolean(8, room.hasTv());
            stmt.setBoolean(9, room.hasWifi());
            stmt.setBoolean(10, room.hasShower());
            stmt.setBoolean(11, room.hasHotwater());
            stmt.setBoolean(12, room.hasFridge());
            stmt.setInt(13, room.getId());
            stmt.setInt(14, room.getVilla());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteRoomFromVilla(int villaId, int roomId) {
        String query = "DELETE FROM room_types WHERE id = ? AND villa = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, roomId);
            stmt.setInt(2, villaId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

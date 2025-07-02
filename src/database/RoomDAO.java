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
}

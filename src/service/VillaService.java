package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.net.httpserver.HttpExchange;
import database.*;
import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class VillaService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final VillaDAO villaDAO = new VillaDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final ReviewDAO reviewDAO = new ReviewDAO();
    private final BookingDAO bookingDAO = new BookingDAO();

    public String getAllVillasAsJson() {
        try {
            List<Villa> villas = villaDAO.getAllVillas();
            return mapper.writeValueAsString(villas);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    public String getVillaByIdAsJson(int id) {
        try {
            Villa villa = villaDAO.getVillaById(id);
            return villa != null ? mapper.writeValueAsString(villa) : "{}";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public String createVilla(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        Villa villa = mapper.readValue(is, Villa.class);
        Villa inserted = villaDAO.insertVilla(villa);
        return mapper.writeValueAsString(inserted);
    }

    public String updateVilla(HttpExchange exchange, int id) throws IOException {
        InputStream is = exchange.getRequestBody();
        Villa villa = mapper.readValue(is, Villa.class);
        villa.setId(id);
        boolean success = villaDAO.updateVilla(villa);
        if (success) {
            return mapper.writeValueAsString(villa);
        } else {
            return "{\"error\":\"Villa not found or update failed\"}";
        }
    }

    public String deleteVilla(int id) {
        boolean success = villaDAO.deleteVilla(id);
        return success ?
                "{\"message\":\"Villa deleted successfully\"}" :
                "{\"error\":\"Deletion failed\"}";
    }

    public String getRoomsByVillaIdAsJson(int villaId) {
        try {
            List<Room> rooms = roomDAO.getRoomsByVillaId(villaId);
            return mapper.writeValueAsString(rooms);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"error\": \"JSON serialization failed\"}";
        }
    }

    public String addRoomToVilla(HttpExchange exchange, int villaId) throws IOException {
        InputStream is = exchange.getRequestBody();
        Room room = mapper.readValue(is, Room.class);
        room.setVilla(villaId);
        Room insertedRoom = roomDAO.insertRoom(room); // pastikan kamu punya insertRoom di RoomDAO
        return mapper.writeValueAsString(insertedRoom);
    }

    public String updateRoom(HttpExchange exchange, int villaId, int roomId) throws IOException {
        InputStream is = exchange.getRequestBody();
        Room room = mapper.readValue(is, Room.class);
        room.setId(roomId);
        room.setVilla(villaId);

        boolean success = roomDAO.updateRoom(room); // pastikan RoomDAO punya updateRoom
        return success ?
                mapper.writeValueAsString(room) :
                "{\"error\":\"Update room failed\"}";
    }

    public String deleteRoomFromVilla(int villaId, int roomId) {
        boolean success = roomDAO.deleteRoomFromVilla(villaId, roomId); // pastikan RoomDAO punya method ini
        return success ?
                "{\"message\":\"Room deleted successfully\"}" :
                "{\"error\":\"Room deletion failed\"}";
    }

    public String getBookingsByVillaIdAsJson(int villaId) {
        try {
            List<Booking> bookings = bookingDAO.getBookingsByVillaId(villaId);
            return mapper.writeValueAsString(bookings);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"error\":\"JSON serialization failed\"}";
        }
    }

    public String getReviewsByVillaIdAsJson(int villaId) {
        try {
            List<Reviews> reviews = reviewDAO.getReviewsByVillaId(villaId);
            return mapper.writeValueAsString(reviews);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"error\":\"JSON serialization failed\"}";
        }
    }

    public String searchVillaByDateAsJson(String queryString) {
        // Belum diimplementasikan: parsing queryString (ci_date & co_date)
        return "[]";
    }
}

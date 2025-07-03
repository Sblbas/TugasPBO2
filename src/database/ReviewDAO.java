package database;

import model.Reviews;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    // Ambil semua review berdasarkan ID villa
    public List<Reviews> getReviewsByVillaId(int villaId) {
        List<Reviews> reviews = new ArrayList<>();
        String query = """
                    SELECT rv.booking, rv.star, rv.title, rv.content
                    FROM reviews rv
                    JOIN bookings b ON rv.booking = b.id
                    JOIN room_types rt ON b.room_type = rt.id
                    WHERE rt.villa = ?
                """;

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, villaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reviews review = new Reviews();
                review.setBooking(rs.getInt("booking"));
                review.setStar(rs.getInt("star"));
                review.setTitle(rs.getString("title"));
                review.setContent(rs.getString("content"));
                reviews.add(review);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public List<Reviews> getReviewsByCustomerId(int customerId) {
        List<Reviews> reviews = new ArrayList<>();
        String query = """
                SELECT r.* FROM reviews r
                JOIN bookings b ON r.booking = b.id
                WHERE b.customer = ?
                """;

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reviews review = new Reviews();
                review.setBooking(rs.getInt("booking"));
                review.setStar(rs.getInt("star"));
                review.setTitle(rs.getString("title"));
                review.setContent(rs.getString("content"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public Reviews getReviewByCustomerBooking(int customerId, int bookingId) {
        String query = """
                    SELECT r.* FROM reviews r
                    JOIN bookings b ON r.booking = b.id
                    WHERE b.id = ? AND b.customer = ?
                """;

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            stmt.setInt(2, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Reviews review = new Reviews();
                review.setBooking(rs.getInt("booking"));
                review.setStar(rs.getInt("star"));
                review.setTitle(rs.getString("title"));
                review.setContent(rs.getString("content"));
                return review;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Tambahkan reviews baru
    public boolean createReview(Reviews reviews) {
        String query = "INSERT INTO reviews (booking, star, title, content) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reviews.getBooking());
            stmt.setInt(2, reviews.getStar());
            stmt.setString(3, reviews.getTitle());
            stmt.setString(4, reviews.getContent());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

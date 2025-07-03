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

    // Hapus review berdasarkan booking ID
    public boolean deleteReviewByBookingId(int bookingId) {
        String query = "DELETE FROM reviews WHERE booking = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // (Opsional) Update reviews
    public boolean updateReview(Reviews reviews) {
        String query = "UPDATE reviews SET star = ?, title = ?, content = ? WHERE booking = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reviews.getStar());
            stmt.setString(2, reviews.getTitle());
            stmt.setString(3, reviews.getContent());
            stmt.setInt(4, reviews.getBooking());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

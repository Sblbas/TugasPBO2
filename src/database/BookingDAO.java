package database;

import model.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public List<Booking> getBookingsByVillaId(int villaId) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings b " +
                "JOIN room_types r ON b.room_type = r.id " +
                "WHERE r.villa = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, villaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setCustomer(rs.getInt("customer"));
                booking.setRoom_type(rs.getInt("room_type"));
                booking.setCheckin_date(rs.getString("checkin_date"));
                booking.setCheckout_date(rs.getString("checkout_date"));
                booking.setPrice(rs.getInt("price"));

                int voucherVal = rs.getInt("voucher");
                if (rs.wasNull()) {
                    booking.setVoucher(null);
                } else {
                    booking.setVoucher(voucherVal);
                }

                booking.setFinal_price(rs.getInt("final_price"));
                booking.setPayment_status(rs.getString("payment_status"));
                booking.setHas_checkedin(rs.getBoolean("has_checkedin"));
                booking.setHas_checkedout(rs.getBoolean("has_checkedout"));

                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public List<Booking> getBookingsByCustomerId(int customerId) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE customer = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setCustomer(rs.getInt("customer"));
                booking.setRoom_type(rs.getInt("room_type"));
                booking.setCheckin_date(rs.getString("checkin_date"));
                booking.setCheckout_date(rs.getString("checkout_date"));
                booking.setPrice(rs.getInt("price"));
                booking.setVoucher(rs.getInt("voucher"));
                booking.setFinal_price(rs.getInt("final_price"));
                booking.setPayment_status(rs.getString("payment_status"));
                booking.setHas_checkedin(rs.getBoolean("has_checkedin"));
                booking.setHas_checkedout(rs.getBoolean("has_checkedout"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public void insertBooking(Booking booking) {
        String query = """
        INSERT INTO bookings (customer, room_type, checkin_date, checkout_date, price, voucher, final_price, payment_status, has_checkedin, has_checkedout)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, booking.getCustomer());
            stmt.setInt(2, booking.getRoom_type());
            stmt.setString(3, booking.getCheckin_date());  // dalam bentuk string, bukan LocalDateTime
            stmt.setString(4, booking.getCheckout_date());
            stmt.setInt(5, booking.getPrice());

            if (booking.getVoucher() != null) {
                stmt.setInt(6, booking.getVoucher());
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }

            stmt.setInt(7, booking.getFinal_price());
            stmt.setString(8, booking.getPayment_status());
            stmt.setBoolean(9, booking.isHas_checkedin());
            stmt.setBoolean(10, booking.isHas_checkedout());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                booking.setId(keys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateBooking(Booking booking) {
        String query = "UPDATE bookings SET customer = ?, room_type = ?, checkin_date = ?, checkout_date = ?, price = ?, " +
                "voucher = ?, final_price = ?, payment_status = ?, has_checkedin = ?, has_checkedout = ? " +
                "WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, booking.getCustomer());
            stmt.setInt(2, booking.getRoom_type());
            stmt.setString(3, booking.getCheckin_date());
            stmt.setString(4, booking.getCheckout_date());
            stmt.setInt(5, booking.getPrice());

            if (booking.getVoucher() != null) {
                stmt.setInt(6, booking.getVoucher());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setInt(7, booking.getFinal_price());
            stmt.setString(8, booking.getPayment_status());
            stmt.setBoolean(9, booking.isHas_checkedin());
            stmt.setBoolean(10, booking.isHas_checkedout());
            stmt.setInt(11, booking.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBooking(int id) {
        String query = "DELETE FROM bookings WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

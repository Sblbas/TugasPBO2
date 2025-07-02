package service;

import database.Database;
import model.Voucher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VoucherService {

    public void createVoucher(Voucher voucher) throws Exception {
        String sql = "INSERT INTO vouchers (code, description, discount, start_date, end_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, voucher.getCode());
            stmt.setString(2, voucher.getDescription());
            stmt.setDouble(3, voucher.getDiscount());
            stmt.setObject(4, voucher.getStartDate());  // assuming JDBC 4.2+ with java.time support
            stmt.setObject(5, voucher.getEndDate());

            stmt.executeUpdate();
        }
    }


    public List<Voucher> getAllVouchers() throws Exception {
        List<Voucher> list = new ArrayList<>();
        String sql = "SELECT * FROM vouchers";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Voucher v = new Voucher();
                v.setId(rs.getInt("id"));
                v.setCode(rs.getString("code"));
                v.setDiscount(rs.getDouble("discount"));
                list.add(v);
            }
        }

        return list;
    }
}

package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import database.VoucherDAO;
import model.Voucher;

import java.util.List;

public class VoucherService {
    private final VoucherDAO voucherDAO = new VoucherDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    public String getAllVouchersAsJson() {
        try {
            List<Voucher> vouchers = voucherDAO.getAllVouchers();
            return mapper.writeValueAsString(vouchers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"error\":\"Failed to convert to JSON\"}";
        }
    }

    public String getVoucherByIdAsJson(int id) {
        Voucher voucher = voucherDAO.getVoucherById(id);
        try {
            return voucher != null ?
                    mapper.writeValueAsString(voucher) :
                    "{\"error\":\"Voucher not found\"}";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"error\":\"Failed to convert to JSON\"}";
        }
    }

    public boolean createVoucher(Voucher voucher) {
        return voucherDAO.insertVoucher(voucher);
    }

    public boolean updateVoucher(int id, Voucher voucher) {
        return voucherDAO.updateVoucher(id, voucher);
    }

    public boolean deleteVoucher(int id) {
        return voucherDAO.deleteVoucher(id);
    }
}

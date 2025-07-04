package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Voucher;
import service.VoucherService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VoucherController implements HttpHandler {

    private final VoucherService voucherService = new VoucherService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String[] segments = path.split("/");
        String response = "";
        int status = 200;

        try {
            if ("GET".equalsIgnoreCase(method) && segments.length == 2) {
                // GET /vouchers
                response = voucherService.getAllVouchersAsJson();
            } else if ("GET".equalsIgnoreCase(method) && segments.length == 3) {
                // GET /vouchers/{id}
                int id = Integer.parseInt(segments[2]);
                response = voucherService.getVoucherByIdAsJson(id);
            } else if ("POST".equalsIgnoreCase(method) && segments.length == 2) {
                // POST /vouchers
                InputStream is = exchange.getRequestBody();
                Voucher voucher = objectMapper.readValue(is, Voucher.class);
                boolean created = voucherService.createVoucher(voucher);
                response = created ? "{\"message\":\"Voucher created\"}" : "{\"error\":\"Failed to create voucher\"}";
                status = created ? 201 : 400;
            } else if ("PUT".equalsIgnoreCase(method) && segments.length == 3) {
                // PUT /vouchers/{id}
                int id = Integer.parseInt(segments[2]);
                InputStream is = exchange.getRequestBody();
                Voucher voucher = objectMapper.readValue(is, Voucher.class);
                boolean updated = voucherService.updateVoucher(id, voucher);
                response = updated ? "{\"message\":\"Voucher updated\"}" : "{\"error\":\"Failed to update voucher\"}";
            } else if ("DELETE".equalsIgnoreCase(method) && segments.length == 3) {
                // DELETE /vouchers/{id}
                int id = Integer.parseInt(segments[2]);
                boolean deleted = voucherService.deleteVoucher(id);
                response = deleted ? "{\"message\":\"Voucher deleted\"}" : "{\"error\":\"Failed to delete voucher\"}";
            } else {
                status = 404;
                response = "{\"error\":\"Not Found\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = 500;
            response = "{\"error\":\"Internal Server Error\"}";
        }

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

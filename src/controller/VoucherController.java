package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Voucher;
import service.VoucherService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class VoucherController implements HttpHandler {

    private final VoucherService voucherService = new VoucherService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // Optional: validasi API key
            util.APIKeyValidator.rejectIfInvalid(exchange);

            String method = exchange.getRequestMethod();
            if ("GET".equalsIgnoreCase(method)) {
                List<Voucher> vouchers = voucherService.getAllVouchers();
                String response = objectMapper.writeValueAsString(vouchers);
                sendResponse(exchange, 200, response);
            } else if ("POST".equalsIgnoreCase(method)) {
                InputStream is = exchange.getRequestBody();
                Voucher voucher = objectMapper.readValue(is, Voucher.class);
                voucherService.createVoucher(voucher);
                sendResponse(exchange, 201, "{\"message\":\"Voucher created successfully\"}");
            } else {
                sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
            }
        } catch (Exception e) {
            exception.GlobalExceptionHandler.handle(exchange, e);
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String responseText) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseText.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseText.getBytes());
        os.close();
    }
}

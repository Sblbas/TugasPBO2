package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Customer;
import service.CustomerService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class CustomerController implements HttpHandler {

    private final CustomerService customerService = new CustomerService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // ✅ Validasi API Key (opsional)
            util.APIKeyValidator.rejectIfInvalid(exchange);

            String method = exchange.getRequestMethod();
            if ("GET".equalsIgnoreCase(method)) {
                List<Customer> customers = customerService.getAllCustomers();
                String response = objectMapper.writeValueAsString(customers);
                sendResponse(exchange, 200, response);
            } else if ("POST".equalsIgnoreCase(method)) {
                InputStream is = exchange.getRequestBody();
                Customer customer = objectMapper.readValue(is, Customer.class);
                customerService.createCustomer(customer);
                sendResponse(exchange, 201, "{\"message\":\"Customer created successfully\"}");
            } else {
                sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
            }
        } catch (Exception e) {
            // ✅ Tangani semua error secara global
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

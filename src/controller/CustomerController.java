package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Customer;
import model.Booking;
import model.Reviews;
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
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String[] segments = path.split("/");

        String json = "";
        int statusCode = 200;

        try {
            // GET /customers
            if ("GET".equalsIgnoreCase(method) && segments.length == 2) {
                json = objectMapper.writeValueAsString(customerService.getAllCustomers());
            }

            // GET /customers/{id}
            else if ("GET".equalsIgnoreCase(method) && segments.length == 3) {
                int id = Integer.parseInt(segments[2]);
                json = objectMapper.writeValueAsString(customerService.getCustomerById(id));
            }

            // GET /customers/{id}/bookings
            else if ("GET".equalsIgnoreCase(method) && segments.length == 4 && segments[3].equals("bookings")) {
                int id = Integer.parseInt(segments[2]);
                json = customerService.getBookingsByCustomerIdAsJson(id);
            }

            // GET /customers/{id}/reviews
            else if ("GET".equalsIgnoreCase(method) && segments.length == 4 && segments[3].equals("reviews")) {
                int customerId = Integer.parseInt(segments[2]);
                json = customerService.getReviewsByCustomerId(customerId);
            }
            // GET /customers/{id}/bookings/{id}/reviews
            else if("GET".equalsIgnoreCase(method) && segments.length == 6 && segments[3].equals("bookings") && segments[5].equals("reviews")){
                int customerId = Integer.parseInt(segments[2]);
                int bookingId = Integer.parseInt(segments[4]);
                json = customerService.getReviewsByCustomerBookingAsJson(customerId, bookingId);
            }
            // POST /customers
            else if ("POST".equalsIgnoreCase(method) && segments.length == 2) {
                InputStream is = exchange.getRequestBody();
                Customer customer = objectMapper.readValue(is, Customer.class);
                customerService.createCustomer(customer);
                json = "{\"message\":\"Customer created\"}";
                statusCode = 201;
            }

            // POST /customers/{id}/bookings
            else if ("POST".equalsIgnoreCase(method) && segments.length == 4 && segments[3].equals("bookings")) {
                int customerId = Integer.parseInt(segments[2]);
                InputStream is = exchange.getRequestBody();
                Booking booking = objectMapper.readValue(is, Booking.class);
                customerService.createBooking(customerId, booking);
                json = "{\"message\":\"Booking created successfully\"}";
                statusCode = 201;
            }

            // POST /customers/{id}/bookings/{id}/reviews
            else if ("POST".equalsIgnoreCase(method) && segments.length == 6 && segments[3].equals("bookings") && segments[5].equals("reviews")) {

                int customerId = Integer.parseInt(segments[2]);
                int bookingId = Integer.parseInt(segments[4]);
                InputStream is = exchange.getRequestBody();

                // baca isi JSON menjadi objek Reviews
                Reviews review = objectMapper.readValue(is, Reviews.class);

                // proses simpan
                customerService.createReview(bookingId, review);

                json = "{\"message\":\"Review berhasil ditambahkan\"}";
                statusCode = 201;
            }

            // PUT /customers/{id}
            else if ("PUT".equalsIgnoreCase(method) && segments.length == 3) {
                int id = Integer.parseInt(segments[2]);
                InputStream is = exchange.getRequestBody();
                Customer customer = objectMapper.readValue(is, Customer.class);
                customerService.updateCustomer(id, customer);
            }


            else {
                statusCode = 404;
                json = "{\"error\":\"Not Found\"}";
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusCode = 500;
            json = "{\"error\":\"Internal Server Error\"}";
        }

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, json.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(json.getBytes());
        os.close();
    }

}

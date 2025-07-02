package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.VillaService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class VillaController implements HttpHandler {
    private final VillaService villaService = new VillaService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        URI requestURI = exchange.getRequestURI();
        String path = requestURI.getPath();
        String query = requestURI.getQuery();
        String[] segments = path.split("/");

        String json = "";
        int statusCode = 200;

        try {
            // GET /villas
            if ("GET".equalsIgnoreCase(method) && segments.length == 2) {
                json = villaService.getAllVillasAsJson();
            }

            // GET /villas/{id}
            else if ("GET".equalsIgnoreCase(method) && segments.length == 3) {
                int id = Integer.parseInt(segments[2]);
                json = villaService.getVillaByIdAsJson(id);
            }

            // GET /villas/{id}/rooms
            else if ("GET".equalsIgnoreCase(method) && segments.length == 4 && segments[3].equals("rooms")) {
                int id = Integer.parseInt(segments[2]);
                json = villaService.getRoomsByVillaIdAsJson(id);
            }

            // GET /villas/{id}/bookings
            else if ("GET".equalsIgnoreCase(method) && segments.length == 4 && segments[3].equals("bookings")) {
                int id = Integer.parseInt(segments[2]);
                json = villaService.getBookingsByVillaIdAsJson(id);
            }

            // GET /villas/{id}/reviews
            else if ("GET".equalsIgnoreCase(method) && segments.length == 4 && segments[3].equals("reviews")) {
                int id = Integer.parseInt(segments[2]);
                json = villaService.getReviewsByVillaIdAsJson(id);
            }

            // GET /villas?ci_date=...&co_date=...
            else if ("GET".equalsIgnoreCase(method) && segments.length == 2 && query != null) {
                json = villaService.searchVillaByDateAsJson(query);
            }

            // POST /villas
            else if ("POST".equalsIgnoreCase(method) && segments.length == 2) {
                json = villaService.createVilla(exchange);
                statusCode = 201;
            }

            // POST /villas/{id}/rooms
            else if ("POST".equalsIgnoreCase(method) && segments.length == 4 && segments[3].equals("rooms")) {
                int id = Integer.parseInt(segments[2]);
                json = villaService.addRoomToVilla(exchange, id);
                statusCode = 201;
            }

            // DELETE /villas/{id}
            else if ("DELETE".equalsIgnoreCase(method) && segments.length == 3) {
                int id = Integer.parseInt(segments[2]);
                json = villaService.deleteVilla(id);
            }

            // PUT /villas/{id}
            else if ("PUT".equalsIgnoreCase(method) && segments.length == 3) {
                int id = Integer.parseInt(segments[2]);
                json = villaService.updateVilla(exchange, id);
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

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, json.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(json.getBytes());
        os.close();
    }
}
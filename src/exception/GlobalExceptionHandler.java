package exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GlobalExceptionHandler {

    public static void handle(HttpExchange exchange, Exception e) {
        int statusCode = 500;
        String message = "Terjadi kesalahan pada server";

        if (e instanceof BadRequestException) {
            statusCode = 400;
            message = e.getMessage();
        } else if (e instanceof NotFoundException) {
            statusCode = 404;
            message = e.getMessage();
        } else if (e instanceof SecurityException) {
            statusCode = 403;
            message = e.getMessage(); // "API key tidak valid" misalnya
        }

        sendJsonResponse(exchange, statusCode, Map.of(
                "success", false,
                "error", message
        ));
    }

    private static void sendJsonResponse(HttpExchange exchange, int statusCode, Map<String, Object> responseMap) {
        try {
            String responseJson = new ObjectMapper().writeValueAsString(responseMap);
            byte[] responseBytes = responseJson.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(statusCode, responseBytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        } catch (IOException ex) {
            System.err.println("❌ Gagal mengirim respons error: " + ex.getMessage());
        }
    }
}

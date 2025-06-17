package src.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import src.model.Villa;
import src.service.VillaService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class VillaController implements HttpHandler {

    private final VillaService villaService;
    private final ObjectMapper objectMapper;

    public VillaController() {
        this.villaService = new VillaService();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if ("GET".equalsIgnoreCase(method) && "/villas".equalsIgnoreCase(path)) {
            handleGetAllVillas(exchange);
        } else if ("POST".equalsIgnoreCase(method) && "/villas".equalsIgnoreCase(path)) {
            handleCreateVilla(exchange);
        } else {
            sendResponse(exchange, 404, "{\"error\": \"Endpoint not found\"}");
        }
    }

    private void handleGetAllVillas(HttpExchange exchange) throws IOException {
        try {
            String jsonData = villaService.getAllVillasAsJson();
            sendResponse(exchange, 200, jsonData);
        } catch (Exception e) {
            sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    private void handleCreateVilla(HttpExchange exchange) throws IOException {
        try {
            Villa villa = objectMapper.readValue(exchange.getRequestBody(), Villa.class);
            villaService.createVilla(villa);
            sendResponse(exchange, 201, "{\"message\": \"Villa created successfully\"}");
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
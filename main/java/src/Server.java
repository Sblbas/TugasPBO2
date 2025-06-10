package main.java.src;

import java.io.*;
import java.net.*;
import java.util.*;

import main.java.src.controller.VillaController;

public class Server {

    private int port;
    private VillaController villaController;

    public Server(int port) {
        this.port = port;
        this.villaController = new VillaController();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server berjalan di port " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Baca permintaan dari client
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                Request request = new Request(in);
                Response response;

                // Cek API key dari header
                String apiKey = request.getHeader("x-api-key");
                if (apiKey == null || !apiKey.equals(Main.API_KEY)) {
                    response = new Response(401, "Unauthorized", "{\"error\": \"API key invalid\"}");
                } else {
                    // Routing endpoint vila
                    if (request.getMethod().equals("GET") && request.getPath().equals("/villas")) {
                        response = villaController.getAllVillas();
                    } else {
                        response = new Response(404, "Not Found", "{\"error\": \"Endpoint tidak ditemukan\"}");
                    }
                }

                // Kirim response ke client
                out.write(response.toHttpResponse());
                out.flush();

                // Tutup koneksi
                in.close();
                out.close();
                clientSocket.close();
            }

        } catch (IOException e) {
            System.err.println("Terjadi kesalahan pada server: " + e.getMessage());
        }
    }
}

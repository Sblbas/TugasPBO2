package Tugas2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import controller.VillaController;

public class Server {
    private HttpServer server;

    private class RequestHandler implements HttpHandler {
        public void handle(HttpExchange httpExchange) {
            Server.processHttpExchange(httpExchange);
        }
    }

    public Server(int port) throws Exception {
        server = HttpServer.create(new InetSocketAddress(port), 128);

        // Tambahkan endpoint VillaController
        server.createContext("/villas", new VillaController());

        // Endpoint default
        server.createContext("/", new RequestHandler());

        server.start();
        System.out.println("Server berjalan di port " + port);
    }

    public static void processHttpExchange(HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        System.out.printf("path: %s\n", path);

        // Handle request dan autentikasi dalam block try-catch
        try {
            Request req = new Request(httpExchange);
            Response res = new Response(httpExchange);

            // Contoh log request JSON
            Map<String, Object> reqJsonMap = req.getJSON();
            System.out.println("first_name => " + reqJsonMap.get("first_name"));
            System.out.println("email => " + reqJsonMap.get("email"));

            // Kirim respons sukses
            res.sendJSON(Map.of("message", "Request berhasil diproses"), 200);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            try {
                Response res = new Response(httpExchange);
                res.sendJSON(Map.of("error", e.getMessage()), 500);
            } catch (Exception ignored) {}
        }
    }
}
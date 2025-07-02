package Tugas2;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.net.InetSocketAddress;
import java.net.URI;

import controller.VillaController;
import controller.CustomerController; // ✅ Tambahkan
import controller.VoucherController;  // ✅ Tambahkan

public class Server {
    private HttpServer server;

    private class RequestHandler implements HttpHandler {
        public void handle(HttpExchange httpExchange) {
            Server.processHttpExchange(httpExchange);
        }
    }

    public Server(int port) throws Exception {
        server = HttpServer.create(new InetSocketAddress(port), 128);

        // ✅ Tambahkan semua endpoint controller di sini
        server.createContext("/villas", new VillaController());
        server.createContext("/customers", new CustomerController());
        server.createContext("/vouchers", new VoucherController());

        server.createContext("/", new RequestHandler()); // endpoint fallback

        server.start();
        System.out.println("Server berjalan di port " + port);
    }

    // Jika kamu punya method start()
    public void start() {
        System.out.println("Server sudah dijalankan melalui constructor.");
    }

    public static void processHttpExchange(HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        System.out.printf("path: %s\n", path);

        try {
            Request req = new Request(httpExchange);
            Response res = new Response(httpExchange);

            // contoh log request
            System.out.println("Request JSON => " + req.getJSON());

            res.send(200); // default response
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            try {
                Response res = new Response(httpExchange);
                res.setBody("{\"error\":\"" + e.getMessage() + "\"}");
                res.send(500);
            } catch (Exception ignored) {}
        }
    }
}

package app;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.port;

public class Main {
    private static final String API_KEY = "123456"; // Hardcoded sesuai soal

    public static void main(String[] args) {
        port(8081); // Port default

        // Middleware: Autentikasi API Key
        before((req, res) -> {
            String key = req.headers("API-Key");
            if (!API_KEY.equals(key)) {
                halt(401, "{\"error\":\"Unauthorized - Invalid API Key\"}");
            }
        });

        // Tes endpoint
        get("/ping", (req, res) -> {
            res.type("application/json");
            return "{\"status\":\"API is running\"}";
        });

        // Endpoint GET villa (dummy sementara)
        get("/villas", (req, res) -> {
            res.type("application/json");
            return "[{\"id\":1,\"name\":\"Villa Mawar\"},{\"id\":2,\"name\":\"Villa Anggrek\"}]";
        });

        // Anda bisa menambahkan controller sebenarnya nanti
    }
}

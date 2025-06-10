import static spark.Spark.*;
import service.VillaService;

public class Main {
    private static final String API_KEY = "APIKEY123";

    public static void main(String[] args) {
        port(8080);

        before((req, res) -> {
            String key = req.headers("x-api-key");
            if (!API_KEY.equals(key)) {
                halt(401, "{\"error\":\"Unauthorized\"}");
            }
        });

        get("/villas", (req, res) -> {
            res.type("application/json");
            return VillaService.getAllVillas();
        });

        // Tambahkan endpoint lainnya di sini
    }
}

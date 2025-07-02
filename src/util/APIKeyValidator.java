package util;

import com.sun.net.httpserver.HttpExchange;

import java.util.List;

public class APIKeyValidator {
    private static final String VALID_API_KEY = "123456"; // Ganti sesuai kebutuhan

    public static boolean isValid(HttpExchange exchange) {
        List<String> apiKeyHeader = exchange.getRequestHeaders().get("X-API-KEY");
        if (apiKeyHeader == null || apiKeyHeader.isEmpty()) {
            return false;
        }

        String providedKey = apiKeyHeader.get(0);
        return VALID_API_KEY.equals(providedKey);
    }

    public static void rejectIfInvalid(HttpExchange exchange) throws SecurityException {
        if (!isValid(exchange)) {
            throw new SecurityException("API key tidak valid atau tidak disediakan");
        }
    }
}

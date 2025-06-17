package main.java.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String method;
    private String path;
    private Map<String, String> headers;
    private String body;

    public Request(BufferedReader in) throws IOException {
        headers = new HashMap<>();
        String line;

        // Baca request line (contoh: GET /villas HTTP/1.1)
        String requestLine = in.readLine();
        if (requestLine == null || requestLine.isEmpty()) return;

        String[] parts = requestLine.split(" ");
        method = parts[0];
        path = parts[1];

        // Baca headers
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            int colonIndex = line.indexOf(":");
            if (colonIndex > 0) {
                String key = line.substring(0, colonIndex).trim().toLowerCase();
                String value = line.substring(colonIndex + 1).trim();
                headers.put(key, value);
            }
        }

        // Baca body (jika ada)
        StringBuilder bodyBuilder = new StringBuilder();
        while (in.ready()) {
            bodyBuilder.append((char) in.read());
        }
        body = bodyBuilder.toString();
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        return headers.get(key.toLowerCase());
    }

    public String getBody() {
        return body;
    }
}

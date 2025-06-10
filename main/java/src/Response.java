package main.java.src;

public class Response {
    private int statusCode;
    private String statusMessage;
    private String body;

    public Response(int statusCode, String statusMessage, String body) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.body = body;
    }

    public String toHttpResponse() {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusMessage).append("\r\n");
        response.append("Content-Type: application/json\r\n");
        response.append("Content-Length: ").append(body.getBytes().length).append("\r\n");
        response.append("\r\n");
        response.append(body);
        return response.toString();
    }
}

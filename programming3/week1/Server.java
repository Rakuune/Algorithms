import com.sun.net.httpserver.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Server implements HttpHandler {
    private static final List<String> messages = new ArrayList<>();

    private Server() {
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                handlePostRequest(exchange);
            } else if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                handleGetRequest(exchange);
            } else {
                handleUnsupportedRequest(exchange);
            }
        } finally {
            exchange.close();
        }
    }

    private void handlePostRequest(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        String text = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));
        messages.add(text); 
        inputStream.close();
        exchange.sendResponseHeaders(200, -1); 
    }

    private void handleGetRequest(HttpExchange exchange) throws IOException {
        String responseString = messages.isEmpty() ? "No messages" : String.join("\n", messages);
        byte[] bytes = responseString.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    private void handleUnsupportedRequest(HttpExchange exchange) throws IOException {
        String response = "Not supported";
        exchange.sendResponseHeaders(400, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
        server.createContext("/help", new Server());
        server.setExecutor(null);
        server.start();
    }
}

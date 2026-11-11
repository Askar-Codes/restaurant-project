package restaurant.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import restaurant.meal.MealType;
import restaurant.topping.ToppingType;

// A very small web server (built into the JDK). It serves the page web/index.html
// and a few /api/... addresses that call WebOrderSession (which calls RestaurantFacade).
public class RestaurantWebServer {
    private static final String JSON_TYPE = "application/json; charset=utf-8";

    private final HttpServer httpServer;
    private final WebOrderSession orderSession = new WebOrderSession();
    private final Path mainPageFile = Paths.get("web", "index.html");

    public RestaurantWebServer(int port) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/", this::handleMainPageRequest);
        httpServer.createContext("/api/", this::handleApiRequest);
    }

    public void start() {
        httpServer.start();
    }

    private void handleMainPageRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (!path.equals("/") && !path.equals("/index.html")) {
            sendText(exchange, 404, "text/plain; charset=utf-8", "Page not found.");
            return;
        }
        if (!Files.exists(mainPageFile)) {
            sendText(exchange, 500, "text/plain; charset=utf-8",
                    "The file web/index.html was not found. Start the program from the project folder.");
            return;
        }
        sendBytes(exchange, 200, "text/html; charset=utf-8", Files.readAllBytes(mainPageFile));
    }

    private void handleApiRequest(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().getPath();
            QueryParameters parameters = new QueryParameters(exchange.getRequestURI().getRawQuery());
            sendText(exchange, 200, JSON_TYPE, createResponseJsonFor(path, parameters));
        } catch (IllegalArgumentException | IllegalStateException problem) {
            sendText(exchange, 400, JSON_TYPE, "{\"error\":" + JsonText.quote(String.valueOf(problem.getMessage())) + "}");
        } catch (RuntimeException unexpectedProblem) {
            sendText(exchange, 500, JSON_TYPE, "{\"error\":\"Something went wrong inside the restaurant.\"}");
        }
    }

    private String createResponseJsonFor(String path, QueryParameters parameters) {
        switch (path) {
            case "/api/menu":
                return RestaurantMenuJson.buildMenuJson();
            case "/api/state":
                return orderSession.toJson();
            case "/api/start":
                orderSession.startNewOrder(readTableNumber(parameters.get("table")), readCustomerName(parameters.get("name")));
                return orderSession.toJson();
            case "/api/add":
                orderSession.addMealToOrder(MealType.valueOf(parameters.get("meal")), readToppings(parameters.get("toppings")));
                return orderSession.toJson();
            case "/api/comment":
                orderSession.addCommentToOrder(parameters.get("text"));
                return orderSession.toJson();
            case "/api/send":
                orderSession.sendOrderToKitchen();
                return orderSession.toJson();
            case "/api/cook":
                orderSession.startCookingOrder();
                return orderSession.toJson();
            case "/api/ready":
                orderSession.markOrderAsReady();
                return orderSession.toJson();
            case "/api/pay":
                orderSession.payForOrder(parameters.get("method"));
                return orderSession.toJson();
            default:
                throw new IllegalArgumentException("Unknown request: " + path);
        }
    }

    private int readTableNumber(String tableText) {
        try {
            return Integer.parseInt(tableText.trim());
        } catch (NumberFormatException problem) {
            throw new IllegalArgumentException("The table must be a number.");
        }
    }

    private String readCustomerName(String nameText) {
        return nameText.trim().isEmpty() ? "Guest" : nameText.trim();
    }

    private List<ToppingType> readToppings(String toppingsText) {
        List<ToppingType> toppings = new ArrayList<>();
        for (String oneToppingName : toppingsText.split(",")) {
            if (!oneToppingName.trim().isEmpty()) {
                toppings.add(ToppingType.valueOf(oneToppingName.trim()));
            }
        }
        return toppings;
    }

    private void sendText(HttpExchange exchange, int statusCode, String contentType, String text) throws IOException {
        sendBytes(exchange, statusCode, contentType, text.getBytes(StandardCharsets.UTF_8));
    }

    private void sendBytes(HttpExchange exchange, int statusCode, String contentType, byte[] body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.getResponseHeaders().set("Cache-Control", "no-store");
        exchange.sendResponseHeaders(statusCode, body.length);
        try (OutputStream responseBody = exchange.getResponseBody()) {
            responseBody.write(body);
        }
    }
}

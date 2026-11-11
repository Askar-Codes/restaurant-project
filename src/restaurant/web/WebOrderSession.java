package restaurant.web;

import java.util.ArrayList;
import java.util.List;
import restaurant.facade.RestaurantFacade;
import restaurant.meal.Meal;
import restaurant.meal.MealType;
import restaurant.payment.CardPaymentStrategy;
import restaurant.payment.CashPaymentStrategy;
import restaurant.payment.PaymentStrategy;
import restaurant.topping.ToppingType;

// Remembers the order that is on the web page right now and talks to the restaurant ONLY through RestaurantFacade.
// Order status: NO_ORDER -> DRAFT -> NEW -> COOKING -> READY -> PAID
public class WebOrderSession {
    private final RestaurantFacade restaurant = new RestaurantFacade();
    private final ConsoleOutputCapture consoleCapture = new ConsoleOutputCapture();
    private final List<Meal> mealsInCurrentOrder = new ArrayList<>();
    private final List<WebMessage> messagesOfCurrentOrder = new ArrayList<>();

    private String status = "NO_ORDER";
    private int orderNumber = 0;
    private int tableNumber = 0;
    private String customerName = "";
    private String commentForKitchen = "";

    public synchronized void startNewOrder(int tableNumber, String customerName) {
        requireStatusIn("start a new order", "NO_ORDER", "PAID");
        restaurant.startNewOrder(tableNumber, customerName);
        this.orderNumber++;
        this.tableNumber = tableNumber;
        this.customerName = customerName;
        this.commentForKitchen = "";
        mealsInCurrentOrder.clear();
        messagesOfCurrentOrder.clear();
        status = "DRAFT";
    }

    public synchronized void addMealToOrder(MealType mealType, List<ToppingType> toppings) {
        requireStatusIn("add a meal", "DRAFT");
        Meal[] createdMeal = new Meal[1];
        runRestaurantActionAndRememberPrintedLines(() ->
                createdMeal[0] = restaurant.addMealToOrder(mealType, toppings.toArray(new ToppingType[0])));
        mealsInCurrentOrder.add(createdMeal[0]);
    }

    public synchronized void addCommentToOrder(String comment) {
        requireStatusIn("change the note", "DRAFT");
        restaurant.addCommentToOrder(comment);
        commentForKitchen = comment;
    }

    public synchronized void sendOrderToKitchen() {
        requireStatusIn("send the order", "DRAFT");
        if (mealsInCurrentOrder.isEmpty()) {
            throw new IllegalStateException("Add at least one meal before sending the order.");
        }
        runRestaurantActionAndRememberPrintedLines(restaurant::sendOrderToKitchen);
        status = "NEW";
    }

    public synchronized void startCookingOrder() {
        requireStatusIn("start cooking", "NEW");
        runRestaurantActionAndRememberPrintedLines(restaurant::startCookingOrder);
        status = "COOKING";
    }

    public synchronized void markOrderAsReady() {
        requireStatusIn("mark the order as ready", "COOKING");
        runRestaurantActionAndRememberPrintedLines(restaurant::markOrderAsReady);
        status = "READY";
    }

    public synchronized void payForOrder(String paymentMethod) {
        requireStatusIn("pay", "READY");
        PaymentStrategy chosenPaymentStrategy = chooseStrategyFor(paymentMethod);
        runRestaurantActionAndRememberPrintedLines(() -> restaurant.payForOrderUsing(chosenPaymentStrategy));
        status = "PAID";
    }

    public synchronized String toJson() {
        StringBuilder stateJson = new StringBuilder("{");
        stateJson.append("\"status\":").append(JsonText.quote(status));
        stateJson.append(",\"orderNumber\":").append(orderNumber);
        stateJson.append(",\"tableNumber\":").append(tableNumber);
        stateJson.append(",\"customerName\":").append(JsonText.quote(customerName));
        stateJson.append(",\"comment\":").append(JsonText.quote(commentForKitchen));
        stateJson.append(",\"total\":").append(JsonText.money(calculateTotalPrice()));
        stateJson.append(",\"meals\":[").append(buildMealsJson()).append("]");
        stateJson.append(",\"messages\":[").append(buildMessagesJson()).append("]");
        return stateJson.append("}").toString();
    }

    private PaymentStrategy chooseStrategyFor(String paymentMethod) {
        if ("CARD".equals(paymentMethod)) {
            return new CardPaymentStrategy();                  // Strategy
        }
        if ("CASH".equals(paymentMethod)) {
            return new CashPaymentStrategy();                  // Strategy
        }
        throw new IllegalArgumentException("Payment method must be CARD or CASH.");
    }

    private void runRestaurantActionAndRememberPrintedLines(Runnable restaurantAction) {
        consoleCapture.startCapturing();
        try {
            restaurantAction.run();
        } finally {
            rememberPrintedLines(consoleCapture.stopCapturingAndGetPrintedLines());
        }
    }

    private void rememberPrintedLines(List<String> printedLines) {
        for (String printedLine : printedLines) {
            String line = printedLine.trim();
            if (line.startsWith("to cook: ")) {
                messagesOfCurrentOrder.add(new WebMessage("KITCHEN_DISH", line.substring("to cook: ".length())));
            } else if (line.startsWith("[KITCHEN]")) {
                messagesOfCurrentOrder.add(new WebMessage("KITCHEN", textAfterClosingBracket(line)));
            } else if (line.startsWith("[CUSTOMER")) {
                messagesOfCurrentOrder.add(new WebMessage("CUSTOMER", textAfterClosingBracket(line)));
            } else if (line.startsWith("[PAYMENT]")) {
                messagesOfCurrentOrder.add(new WebMessage("PAYMENT", textAfterClosingBracket(line)));
            } else if (line.startsWith("[WARNING]")) {
                messagesOfCurrentOrder.add(new WebMessage("WARNING", textAfterClosingBracket(line)));
            }
        }
    }

    private String textAfterClosingBracket(String line) {
        return line.substring(line.indexOf(']') + 1).trim();
    }

    private double calculateTotalPrice() {
        double total = 0;
        for (Meal meal : mealsInCurrentOrder) {
            total += meal.getPrice();
        }
        return total;
    }

    private String buildMealsJson() {
        StringBuilder mealsJson = new StringBuilder();
        for (Meal meal : mealsInCurrentOrder) {
            if (mealsJson.length() > 0) {
                mealsJson.append(",");
            }
            mealsJson.append("{\"description\":").append(JsonText.quote(meal.getDescription()))
                    .append(",\"price\":").append(JsonText.money(meal.getPrice())).append("}");
        }
        return mealsJson.toString();
    }

    private String buildMessagesJson() {
        StringBuilder messagesJson = new StringBuilder();
        for (WebMessage message : messagesOfCurrentOrder) {
            if (messagesJson.length() > 0) {
                messagesJson.append(",");
            }
            messagesJson.append(message.toJson());
        }
        return messagesJson.toString();
    }

    private void requireStatusIn(String whatTheGuestTriesToDo, String... allowedStatuses) {
        for (String allowedStatus : allowedStatuses) {
            if (allowedStatus.equals(status)) {
                return;
            }
        }
        throw new IllegalStateException("You cannot " + whatTheGuestTriesToDo + " right now (order status: " + status + ").");
    }
}

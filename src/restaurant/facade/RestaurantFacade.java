package restaurant.facade;

import restaurant.meal.Meal;
import restaurant.meal.MealType;
import restaurant.order.Order;
import restaurant.order.OrderBuilder;
import restaurant.payment.PaymentStrategy;
import restaurant.status.CustomerObserver;
import restaurant.status.KitchenObserver;
import restaurant.status.OrderStatus;
import restaurant.status.OrderStatusTracker;
import restaurant.topping.ToppingType;
import restaurant.util.MoneyFormatter;

// Pattern: Facade
// One simple entry point. Main only talks to this class and never sees the other patterns.
public class RestaurantFacade {
    private int nextOrderNumber = 1;
    private OrderBuilder orderBuilder;
    private Order currentOrder;
    private OrderStatusTracker statusTracker;

    public void startNewOrder(int tableNumber, String customerName) {
        orderBuilder = new OrderBuilder()
                .withOrderNumber(nextOrderNumber++)
                .withTableNumber(tableNumber)
                .withCustomerName(customerName);
    }

    public void addMealToOrder(MealType mealType, ToppingType... toppings) {
        Meal meal = mealType.createMeal();              // Factory Method
        for (ToppingType topping : toppings) {
            meal = topping.addTo(meal);                 // Decorator
        }
        orderBuilder.addMeal(meal);                     // Builder
    }

    public void addCommentToOrder(String comment) {
        orderBuilder.withComment(comment);
    }

    public void sendOrderToKitchen() {
        currentOrder = orderBuilder.build();            // Builder
        statusTracker = new OrderStatusTracker(currentOrder);
        statusTracker.addObserver(new KitchenObserver());    // Observer
        statusTracker.addObserver(new CustomerObserver());   // Observer
        statusTracker.changeStatusTo(OrderStatus.NEW);
    }

    public void startCookingOrder() {
        statusTracker.changeStatusTo(OrderStatus.COOKING);
    }

    public void markOrderAsReady() {
        statusTracker.changeStatusTo(OrderStatus.READY);
    }

    public void payForOrderUsing(PaymentStrategy paymentStrategy) {
        paymentStrategy.pay(currentOrder.calculateTotalPrice());   // Strategy
        statusTracker.changeStatusTo(OrderStatus.PAID);
    }

    public void printReceiptForCurrentOrder() {
        System.out.println("----- RECEIPT: order #" + currentOrder.getOrderNumber() + " -----");
        for (Meal meal : currentOrder.getMeals()) {
            System.out.println(meal.getDescription() + "  " + MoneyFormatter.format(meal.getPrice()));
        }
        System.out.println("TOTAL: " + MoneyFormatter.format(currentOrder.calculateTotalPrice()));
    }
}

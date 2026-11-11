package restaurant.status;

import restaurant.meal.Meal;
import restaurant.order.Order;

// Pattern: Observer (concrete observer #1)
public class KitchenObserver implements OrderStatusObserver {

    public void onOrderStatusChanged(Order order, OrderStatus newStatus) {
        System.out.println("[KITCHEN] Order #" + order.getOrderNumber()
                + " (table " + order.getTableNumber() + ") is now " + newStatus);
        if (newStatus == OrderStatus.NEW) {
            printMealsThatMustBeCooked(order);
        }
    }

    private void printMealsThatMustBeCooked(Order order) {
        for (Meal meal : order.getMeals()) {
            System.out.println("          to cook: " + meal.getDescription());
        }
    }
}

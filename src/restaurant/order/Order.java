package restaurant.order;

import java.util.List;
import restaurant.meal.Meal;

// Finished order. It is created only by OrderBuilder and does not change afterwards.
public class Order {
    private final int orderNumber;
    private final int tableNumber;
    private final String customerName;
    private final String comment;
    private final List<Meal> meals;

    Order(int orderNumber, int tableNumber, String customerName, String comment, List<Meal> meals) {
        this.orderNumber = orderNumber;
        this.tableNumber = tableNumber;
        this.customerName = customerName;
        this.comment = comment;
        this.meals = meals;
    }

    public int getOrderNumber() { return orderNumber; }

    public int getTableNumber() { return tableNumber; }

    public String getCustomerName() { return customerName; }

    public String getComment() { return comment; }

    public List<Meal> getMeals() { return meals; }

    public double calculateTotalPrice() {
        double total = 0;
        for (Meal meal : meals) {
            total += meal.getPrice();
        }
        return total;
    }
}

package restaurant.order;

import java.util.ArrayList;
import java.util.List;
import restaurant.meal.Meal;

// Pattern: Builder
// Collects the order data step by step and creates the Order in build().
public class OrderBuilder {
    private int orderNumber;
    private int tableNumber;
    private String customerName = "";
    private String comment = "";
    private final List<Meal> meals = new ArrayList<>();

    public OrderBuilder withOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public OrderBuilder withTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
        return this;
    }

    public OrderBuilder withCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public OrderBuilder withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public OrderBuilder addMeal(Meal meal) {
        meals.add(meal);
        return this;
    }

    public Order build() {
        return new Order(orderNumber, tableNumber, customerName, comment, meals);
    }
}

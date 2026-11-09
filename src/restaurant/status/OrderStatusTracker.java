package restaurant.status;

import java.util.ArrayList;
import java.util.List;
import restaurant.order.Order;

// Pattern: Observer (subject)
// Keeps the list of observers and tells all of them when the status changes.
public class OrderStatusTracker {
    private final Order order;
    private final List<OrderStatusObserver> observers = new ArrayList<>();

    public OrderStatusTracker(Order order) {
        this.order = order;
    }

    public void addObserver(OrderStatusObserver observer) {
        observers.add(observer);
    }

    public void changeStatusTo(OrderStatus newStatus) {
        notifyAllObserversAboutNewStatus(newStatus);
    }

    private void notifyAllObserversAboutNewStatus(OrderStatus newStatus) {
        for (OrderStatusObserver observer : observers) {
            observer.onOrderStatusChanged(order, newStatus);
        }
    }
}

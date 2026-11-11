package restaurant.status;

import restaurant.order.Order;

// Pattern: Observer (observer interface)
// Everyone who wants to know about status changes implements this interface.
public interface OrderStatusObserver {
    void onOrderStatusChanged(Order order, OrderStatus newStatus);
}

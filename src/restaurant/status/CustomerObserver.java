package restaurant.status;

import restaurant.order.Order;

// Pattern: Observer (concrete observer #2)
public class CustomerObserver implements OrderStatusObserver {

    public void onOrderStatusChanged(Order order, OrderStatus newStatus) {
        System.out.println("[CUSTOMER " + order.getCustomerName() + "] " + createMessageFor(newStatus));
    }

    private String createMessageFor(OrderStatus status) {
        switch (status) {
            case NEW:     return "We received your order.";
            case COOKING: return "Your food is being cooked.";
            case READY:   return "Your food is ready, enjoy!";
            case PAID:    return "Payment accepted, thank you!";
            default:      return "";
        }
    }
}

package restaurant.payment;

// Pattern: Strategy (strategy interface)
// Each payment method is one interchangeable strategy.
public interface PaymentStrategy {
    void pay(double amount);
}

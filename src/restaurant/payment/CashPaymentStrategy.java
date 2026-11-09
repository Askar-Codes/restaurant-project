package restaurant.payment;

import restaurant.util.MoneyFormatter;

// Pattern: Strategy (concrete strategy #2)
public class CashPaymentStrategy implements PaymentStrategy {

    public void pay(double amount) {
        System.out.println("[PAYMENT] " + MoneyFormatter.format(amount) + " was paid in cash.");
    }
}

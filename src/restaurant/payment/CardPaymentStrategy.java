package restaurant.payment;

import restaurant.util.MoneyFormatter;

// Pattern: Strategy (concrete strategy #1)
public class CardPaymentStrategy implements PaymentStrategy {

    public void pay(double amount) {
        System.out.println("[PAYMENT] " + MoneyFormatter.format(amount) + " was paid by card.");
    }
}

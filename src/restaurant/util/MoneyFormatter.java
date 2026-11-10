package restaurant.util;

import java.util.Locale;

// Small helper so every class prints prices the same way, for example "$12.50".
public class MoneyFormatter {

    public static String format(double amount) {
        return "$" + String.format(Locale.US, "%.2f", amount);
    }
}

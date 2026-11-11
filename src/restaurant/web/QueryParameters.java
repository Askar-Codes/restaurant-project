package restaurant.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

// Reads the parameters of a web address, for example ?meal=PIZZA&toppings=CHICKEN,MUSHROOMS
public class QueryParameters {
    private final Map<String, String> valuesByName = new HashMap<>();

    public QueryParameters(String rawQuery) {
        if (rawQuery == null) {
            return;
        }
        for (String onePair : rawQuery.split("&")) {
            int equalsSignPosition = onePair.indexOf('=');
            if (equalsSignPosition > 0) {
                String name = decode(onePair.substring(0, equalsSignPosition));
                String value = decode(onePair.substring(equalsSignPosition + 1));
                valuesByName.put(name, value);
            }
        }
    }

    public String get(String name) {
        String value = valuesByName.get(name);
        return value == null ? "" : value;
    }

    private static String decode(String encodedText) {
        try {
            return URLDecoder.decode(encodedText, "UTF-8");
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalStateException(problem);
        }
    }
}

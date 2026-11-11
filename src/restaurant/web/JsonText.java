package restaurant.web;

import java.util.Locale;

// Tiny helper for writing JSON by hand (no libraries needed).
public class JsonText {

    public static String quote(String text) {
        StringBuilder quotedText = new StringBuilder("\"");
        for (char oneCharacter : text.toCharArray()) {
            quotedText.append(escapeOneCharacter(oneCharacter));
        }
        return quotedText.append('"').toString();
    }

    public static String money(double amount) {
        return String.format(Locale.US, "%.2f", amount);
    }

    private static String escapeOneCharacter(char oneCharacter) {
        switch (oneCharacter) {
            case '"':  return "\\\"";
            case '\\': return "\\\\";
            case '\n': return "\\n";
            case '\r': return "";
            case '\t': return "\\t";
            default:
                if (oneCharacter < 0x20) {
                    return String.format("\\u%04x", (int) oneCharacter);
                }
                return String.valueOf(oneCharacter);
        }
    }
}

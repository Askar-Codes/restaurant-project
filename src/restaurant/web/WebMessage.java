package restaurant.web;

// One line that the restaurant printed (kitchen, customer, payment...), shown on the web page.
// Channels: KITCHEN, KITCHEN_DISH, CUSTOMER, PAYMENT, WARNING.
public class WebMessage {
    private final String channel;
    private final String text;

    public WebMessage(String channel, String text) {
        this.channel = channel;
        this.text = text;
    }

    public String toJson() {
        return "{\"channel\":" + JsonText.quote(channel) + ",\"text\":" + JsonText.quote(text) + "}";
    }
}

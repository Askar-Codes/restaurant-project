package restaurant.web;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

// Starts the web version of the restaurant. Run this class, then open http://localhost:8080
// (it opens the browser by itself when the computer allows it).
// Start it from the project folder, because the page is read from web/index.html.
public class WebMain {

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        new RestaurantWebServer(port).start();

        String address = "http://localhost:" + port;
        System.out.println("The diner is open. Visit " + address);
        openBrowserIfPossible(address);
    }

    private static void openBrowserIfPossible(String address) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(address));
            }
        } catch (Exception problem) {
            // Not a problem: the address is printed above and can be opened by hand.
        }
    }
}

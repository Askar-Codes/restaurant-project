package restaurant.web;

import restaurant.meal.Meal;
import restaurant.meal.MealType;
import restaurant.topping.ToppingType;

// Builds the menu (meals and the toppings that fit each meal) as JSON for the web page.
// Names and prices come from the real Meal and Decorator objects, so nothing is typed twice.
public class RestaurantMenuJson {

    public static String buildMenuJson() {
        StringBuilder menuJson = new StringBuilder("{\"meals\":[");
        boolean isFirstMeal = true;
        for (MealType mealType : MealType.values()) {
            if (!isFirstMeal) {
                menuJson.append(",");
            }
            menuJson.append(buildOneMealJson(mealType));
            isFirstMeal = false;
        }
        return menuJson.append("]}").toString();
    }

    private static String buildOneMealJson(MealType mealType) {
        Meal plainMeal = mealType.createMeal();
        return "{\"id\":" + JsonText.quote(mealType.name())
                + ",\"name\":" + JsonText.quote(plainMeal.getDescription())
                + ",\"price\":" + JsonText.money(plainMeal.getPrice())
                + ",\"toppings\":[" + buildToppingsJson(mealType, plainMeal) + "]}";
    }

    private static String buildToppingsJson(MealType mealType, Meal plainMeal) {
        StringBuilder toppingsJson = new StringBuilder();
        for (ToppingType topping : ToppingType.values()) {
            if (!topping.canBeAddedTo(mealType)) {
                continue;
            }
            if (toppingsJson.length() > 0) {
                toppingsJson.append(",");
            }
            toppingsJson.append(buildOneToppingJson(topping, plainMeal));
        }
        return toppingsJson.toString();
    }

    private static String buildOneToppingJson(ToppingType topping, Meal plainMeal) {
        Meal mealWithTopping = topping.addTo(plainMeal);
        double toppingPrice = mealWithTopping.getPrice() - plainMeal.getPrice();
        String toppingName = mealWithTopping.getDescription()
                .substring(plainMeal.getDescription().length() + " + ".length());
        return "{\"id\":" + JsonText.quote(topping.name())
                + ",\"name\":" + JsonText.quote(toppingName)
                + ",\"price\":" + JsonText.money(toppingPrice) + "}";
    }
}

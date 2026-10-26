package restaurant.topping;

import restaurant.meal.Meal;

// Pattern: Decorator (concrete decorator) - for burger
public class BaconDecorator extends MealDecorator {

    public BaconDecorator(Meal wrappedMeal) {
        super(wrappedMeal);
    }

    protected String getToppingName() {
        return "bacon";
    }

    protected double getToppingPrice() {
        return 1.50;
    }
}

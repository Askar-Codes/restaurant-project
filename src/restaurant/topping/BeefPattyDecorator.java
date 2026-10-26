package restaurant.topping;

import restaurant.meal.Meal;

// Pattern: Decorator (concrete decorator) - for burger
public class BeefPattyDecorator extends MealDecorator {

    public BeefPattyDecorator(Meal wrappedMeal) {
        super(wrappedMeal);
    }

    protected String getToppingName() {
        return "beef patty";
    }

    protected double getToppingPrice() {
        return 2.50;
    }
}

package restaurant.topping;

import restaurant.meal.Meal;

// Pattern: Decorator (concrete decorator) - for burger
public class ChickenPattyDecorator extends MealDecorator {

    public ChickenPattyDecorator(Meal wrappedMeal) {
        super(wrappedMeal);
    }

    protected String getToppingName() {
        return "chicken patty";
    }

    protected double getToppingPrice() {
        return 2.00;
    }
}

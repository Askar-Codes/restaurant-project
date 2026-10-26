package restaurant.topping;

import restaurant.meal.Meal;

// Pattern: Decorator (concrete decorator) - for burger
public class PicklesDecorator extends MealDecorator {

    public PicklesDecorator(Meal wrappedMeal) {
        super(wrappedMeal);
    }

    protected String getToppingName() {
        return "pickles";
    }

    protected double getToppingPrice() {
        return 0.50;
    }
}

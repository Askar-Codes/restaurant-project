package restaurant.topping;

import restaurant.meal.Meal;

// Pattern: Decorator (concrete decorator) - for french fries
public class CheeseSauceDecorator extends MealDecorator {

    public CheeseSauceDecorator(Meal wrappedMeal) {
        super(wrappedMeal);
    }

    protected String getToppingName() {
        return "cheese sauce";
    }

    protected double getToppingPrice() {
        return 0.80;
    }
}

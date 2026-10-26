package restaurant.topping;

import restaurant.meal.Meal;

// Pattern: Decorator (concrete decorator) - for french fries
public class TomatoSauceDecorator extends MealDecorator {

    public TomatoSauceDecorator(Meal wrappedMeal) {
        super(wrappedMeal);
    }

    protected String getToppingName() {
        return "tomato sauce";
    }

    protected double getToppingPrice() {
        return 0.50;
    }
}

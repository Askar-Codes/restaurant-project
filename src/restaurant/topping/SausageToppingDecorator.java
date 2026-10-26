package restaurant.topping;

import restaurant.meal.Meal;

// Pattern: Decorator (concrete decorator) - for pizza
public class SausageToppingDecorator extends MealDecorator {

    public SausageToppingDecorator(Meal wrappedMeal) {
        super(wrappedMeal);
    }

    protected String getToppingName() {
        return "sausage";
    }

    protected double getToppingPrice() {
        return 2.00;
    }
}

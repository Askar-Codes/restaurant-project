package restaurant.topping;

import restaurant.meal.Meal;

// Pattern: Decorator (concrete decorator) - for pizza
public class MushroomsToppingDecorator extends MealDecorator {

    public MushroomsToppingDecorator(Meal wrappedMeal) {
        super(wrappedMeal);
    }

    protected String getToppingName() {
        return "mushrooms";
    }

    protected double getToppingPrice() {
        return 1.00;
    }
}

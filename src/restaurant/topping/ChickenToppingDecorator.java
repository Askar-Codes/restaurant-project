package restaurant.topping;

import restaurant.meal.Meal;

// Pattern: Decorator (concrete decorator) - for pizza
public class ChickenToppingDecorator extends MealDecorator {

    public ChickenToppingDecorator(Meal wrappedMeal) {
        super(wrappedMeal);
    }

    protected String getToppingName() {
        return "chicken";
    }

    protected double getToppingPrice() {
        return 2.00;
    }
}

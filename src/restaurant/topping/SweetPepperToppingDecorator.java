package restaurant.topping;

import restaurant.meal.Meal;

// Pattern: Decorator (concrete decorator) - for pizza
public class SweetPepperToppingDecorator extends MealDecorator {

    public SweetPepperToppingDecorator(Meal wrappedMeal) {
        super(wrappedMeal);
    }

    protected String getToppingName() {
        return "sweet pepper";
    }

    protected double getToppingPrice() {
        return 1.00;
    }
}

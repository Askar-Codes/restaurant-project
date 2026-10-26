package restaurant.topping;

import restaurant.meal.Meal;

// Pattern: Decorator (base decorator)
// Wraps a Meal and adds the name and the price of one extra topping.
public abstract class MealDecorator implements Meal {
    protected final Meal wrappedMeal;

    public MealDecorator(Meal wrappedMeal) {
        this.wrappedMeal = wrappedMeal;
    }

    public String getDescription() {
        return wrappedMeal.getDescription() + " + " + getToppingName();
    }

    public double getPrice() {
        return wrappedMeal.getPrice() + getToppingPrice();
    }

    protected abstract String getToppingName();

    protected abstract double getToppingPrice();
}

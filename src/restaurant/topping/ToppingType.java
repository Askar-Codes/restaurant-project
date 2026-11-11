package restaurant.topping;

import java.util.function.Function;
import restaurant.meal.Meal;
import restaurant.meal.MealType;

// Connects each menu topping to the decorator that wraps a meal with it.
// Example: ToppingType.BACON.addTo(burger) returns new BaconDecorator(burger).
// Every topping also knows which meal it belongs to (used to reject wrong combinations).
public enum ToppingType {
    // Pizza toppings
    CHICKEN(MealType.PIZZA, ChickenToppingDecorator::new),
    SAUSAGE(MealType.PIZZA, SausageToppingDecorator::new),
    MUSHROOMS(MealType.PIZZA, MushroomsToppingDecorator::new),
    SWEET_PEPPER(MealType.PIZZA, SweetPepperToppingDecorator::new),

    // Burger toppings
    BEEF_PATTY(MealType.BURGER, BeefPattyDecorator::new),
    CHICKEN_PATTY(MealType.BURGER, ChickenPattyDecorator::new),
    BACON(MealType.BURGER, BaconDecorator::new),
    PICKLES(MealType.BURGER, PicklesDecorator::new),

    // French fries sauces
    CHEESE_SAUCE(MealType.FRENCH_FRIES, CheeseSauceDecorator::new),
    TOMATO_SAUCE(MealType.FRENCH_FRIES, TomatoSauceDecorator::new);

    private final MealType mealThisToppingBelongsTo;
    private final Function<Meal, Meal> decoratorConstructor;

    ToppingType(MealType mealThisToppingBelongsTo, Function<Meal, Meal> decoratorConstructor) {
        this.mealThisToppingBelongsTo = mealThisToppingBelongsTo;
        this.decoratorConstructor = decoratorConstructor;
    }

    public boolean canBeAddedTo(MealType mealType) {
        return mealThisToppingBelongsTo == mealType;
    }

    public Meal addTo(Meal meal) {
        return decoratorConstructor.apply(meal);
    }
}

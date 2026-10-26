package restaurant.topping;

import java.util.function.Function;
import restaurant.meal.Meal;

// Connects each menu topping to the decorator that wraps a meal with it.
// Example: ToppingType.BACON.addTo(burger) returns new BaconDecorator(burger).
public enum ToppingType {
    // Pizza toppings
    CHICKEN(ChickenToppingDecorator::new),
    SAUSAGE(SausageToppingDecorator::new),
    MUSHROOMS(MushroomsToppingDecorator::new),
    SWEET_PEPPER(SweetPepperToppingDecorator::new),

    // Burger toppings
    BEEF_PATTY(BeefPattyDecorator::new),
    CHICKEN_PATTY(ChickenPattyDecorator::new),
    BACON(BaconDecorator::new),
    PICKLES(PicklesDecorator::new),

    // French fries sauces
    CHEESE_SAUCE(CheeseSauceDecorator::new),
    TOMATO_SAUCE(TomatoSauceDecorator::new);

    private final Function<Meal, Meal> decoratorConstructor;

    ToppingType(Function<Meal, Meal> decoratorConstructor) {
        this.decoratorConstructor = decoratorConstructor;
    }

    public Meal addTo(Meal meal) {
        return decoratorConstructor.apply(meal);
    }
}

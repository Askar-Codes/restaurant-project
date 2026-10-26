package restaurant.meal;

// Connects each menu item to the factory that creates it.
public enum MealType {
    PIZZA(new PizzaFactory()),
    BURGER(new BurgerFactory()),
    FRENCH_FRIES(new FrenchFriesFactory());

    private final MealFactory factoryForThisMeal;

    MealType(MealFactory factoryForThisMeal) {
        this.factoryForThisMeal = factoryForThisMeal;
    }

    public Meal createMeal() {
        return factoryForThisMeal.createMeal();
    }
}

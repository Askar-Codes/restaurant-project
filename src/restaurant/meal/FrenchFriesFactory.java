package restaurant.meal;

// Pattern: Factory Method (concrete creator)
public class FrenchFriesFactory implements MealFactory {

    public Meal createMeal() {
        return new FrenchFries();
    }
}

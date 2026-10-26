package restaurant.meal;

// Pattern: Factory Method (concrete creator)
public class PizzaFactory implements MealFactory {

    public Meal createMeal() {
        return new Pizza();
    }
}

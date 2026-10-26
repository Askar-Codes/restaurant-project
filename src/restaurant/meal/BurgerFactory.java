package restaurant.meal;

// Pattern: Factory Method (concrete creator)
public class BurgerFactory implements MealFactory {

    public Meal createMeal() {
        return new Burger();
    }
}

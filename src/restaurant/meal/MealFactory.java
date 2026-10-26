package restaurant.meal;

// Pattern: Factory Method
// Creator interface. Every concrete factory decides which Meal it creates.
public interface MealFactory {
    Meal createMeal();
}

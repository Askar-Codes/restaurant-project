package restaurant.meal;

// Common interface for every dish. Decorators and factories both work with this type.
public interface Meal {
    String getDescription();

    double getPrice();
}

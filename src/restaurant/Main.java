package restaurant;

import restaurant.facade.RestaurantFacade;
import restaurant.meal.MealType;
import restaurant.payment.CardPaymentStrategy;
import restaurant.payment.CashPaymentStrategy;
import restaurant.topping.ToppingType;

// Demo: two orders go through the whole restaurant process.
public class Main {

    public static void main(String[] args) {
        RestaurantFacade restaurant = new RestaurantFacade();

        runFirstOrderPaidByCard(restaurant);
        System.out.println();
        runSecondOrderPaidByCash(restaurant);
    }

    private static void runFirstOrderPaidByCard(RestaurantFacade restaurant) {
        System.out.println("===== ORDER 1 =====");
        restaurant.startNewOrder(3, "Anna");
        restaurant.addMealToOrder(MealType.PIZZA, ToppingType.CHICKEN, ToppingType.MUSHROOMS);
        restaurant.addMealToOrder(MealType.BURGER, ToppingType.BEEF_PATTY, ToppingType.BACON);
        restaurant.addMealToOrder(MealType.FRENCH_FRIES, ToppingType.CHEESE_SAUCE);
        restaurant.addCommentToOrder("No onions, please");

        restaurant.sendOrderToKitchen();
        restaurant.startCookingOrder();
        restaurant.markOrderAsReady();
        restaurant.printReceiptForCurrentOrder();
        restaurant.payForOrderUsing(new CardPaymentStrategy());
    }

    private static void runSecondOrderPaidByCash(RestaurantFacade restaurant) {
        System.out.println("===== ORDER 2 =====");
        restaurant.startNewOrder(5, "Daniel");
        restaurant.addMealToOrder(MealType.PIZZA, ToppingType.SAUSAGE, ToppingType.SWEET_PEPPER);
        restaurant.addMealToOrder(MealType.FRENCH_FRIES, ToppingType.TOMATO_SAUCE);

        restaurant.sendOrderToKitchen();
        restaurant.startCookingOrder();
        restaurant.markOrderAsReady();
        restaurant.printReceiptForCurrentOrder();
        restaurant.payForOrderUsing(new CashPaymentStrategy());
    }
}

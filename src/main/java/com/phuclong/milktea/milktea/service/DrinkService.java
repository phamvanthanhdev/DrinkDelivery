package com.phuclong.milktea.milktea.service;

import com.phuclong.milktea.milktea.model.Category;
import com.phuclong.milktea.milktea.model.Drink;
import com.phuclong.milktea.milktea.model.Restaurant;
import com.phuclong.milktea.milktea.request.CreateDrinkRequest;

import java.util.List;

public interface DrinkService {
    public Drink createDrink(CreateDrinkRequest req, Category category, Restaurant restaurant);
    public void deleteDrink(Long drinkId) throws Exception;
    public List<Drink> getRestaurantsDrink(
            Long restaurantId, boolean isVegetarian,
            boolean isNonveg, boolean isSeasonal,
            String drinkCategory);
    public List<Drink> getRestaurantsAllDrink(
            Long restaurantId);
    public List<Drink> searchDrink(String keyword);
    public Drink findDrinkById(Long drinkId) throws Exception;
    public Drink updateAvailabilityStatus(Long drinkId) throws Exception;
    public List<Drink> getAllDrinks();
    public List<Drink> getDrinksRestaurantNotPromotion(Restaurant restaurant);
}

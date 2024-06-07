package com.phuclong.milktea.milktea.service;


import com.phuclong.milktea.milktea.design.decorator.Promotion;
import com.phuclong.milktea.milktea.model.Drink;
import com.phuclong.milktea.milktea.model.Restaurant;
import com.phuclong.milktea.milktea.request.AddPromotionRequest;

import java.util.List;

public interface PromotionService {
    public Promotion findPromotionById(Long id) throws Exception;
    public List<Promotion> getPromotionsRestaurant(Long restaurantId);
    public void deletePromotion(Long id) throws Exception;
    public Promotion updatePromotionStatus(Long id) throws Exception;
    public Promotion createPromotion(AddPromotionRequest req, Restaurant restaurant) throws Exception;
    public Drink getDrinkPromotion(Long drinkId) throws Exception;
    public List<Drink> getDrinksRestaurantPromotion(Restaurant restaurant) throws Exception;
    public List<Drink> getFilterRestaurantsDrink(Long restaurantId,
                                                 boolean isVegetarian,
                                                 boolean isNonveg,
                                                 boolean isSeasonal,
                                                 String drinkCategory);
}

package com.phuclong.milktea.milktea.repository;

import com.phuclong.milktea.milktea.model.Drink;
import com.phuclong.milktea.milktea.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
    List<Drink> findByRestaurantId(Long restaurantId);
    @Query("SELECT d FROM Drink d WHERE d.name LIKE %:keyword% " +
            "OR d.drinkCategory.name LIKE %:keyword%")
    List<Drink> searchDrink(@Param("keyword") String keyword);
    @Query("SELECT d FROM Drink d WHERE d.restaurant = :restaurant AND d NOT IN (SELECT p.drinks FROM Promotion p)")
    List<Drink> findDrinksByRestaurantNotInPromotion(@Param("restaurant") Restaurant restaurant);
}

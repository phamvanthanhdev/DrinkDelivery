package com.phuclong.milktea.milktea.controller;

import com.phuclong.milktea.milktea.design.decorator.Promotion;
import com.phuclong.milktea.milktea.model.Drink;
import com.phuclong.milktea.milktea.model.Restaurant;
import com.phuclong.milktea.milktea.model.User;
import com.phuclong.milktea.milktea.request.AddPromotionRequest;
import com.phuclong.milktea.milktea.service.PromotionService;
import com.phuclong.milktea.milktea.service.RestaurantService;
import com.phuclong.milktea.milktea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/promotion")
public class PromotionController {
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private PromotionService promotionService;
    @PostMapping()
    private ResponseEntity<Promotion> createPromotion(@RequestBody AddPromotionRequest req,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        Promotion savedPromotion = promotionService.createPromotion(req, restaurant);

        return new ResponseEntity<>(savedPromotion, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant")
    private ResponseEntity<List<Promotion>> getPromotionsRestaurant(
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        List<Promotion> promotions = promotionService.getPromotionsRestaurant(restaurant.getId());

        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @GetMapping("/drink/{id}")
    private ResponseEntity<Drink> getDrinkPromotion(@PathVariable Long id,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Drink drink = promotionService.getDrinkPromotion(id);

        return new ResponseEntity<>(drink, HttpStatus.OK);
    }

    @GetMapping("/drink/restaurant")
    private ResponseEntity<List<Drink>> getDrinksRestaurantPromotion(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        List<Drink> drinks = promotionService.getDrinksRestaurantPromotion(restaurant);

        return new ResponseEntity<>(drinks, HttpStatus.OK);
    }


}

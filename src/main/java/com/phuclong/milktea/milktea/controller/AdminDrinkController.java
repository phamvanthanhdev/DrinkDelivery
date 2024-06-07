package com.phuclong.milktea.milktea.controller;

import com.phuclong.milktea.milktea.model.Drink;
import com.phuclong.milktea.milktea.model.Restaurant;
import com.phuclong.milktea.milktea.model.User;
import com.phuclong.milktea.milktea.request.CreateDrinkRequest;
import com.phuclong.milktea.milktea.response.MessageResponse;
import com.phuclong.milktea.milktea.service.DrinkService;
import com.phuclong.milktea.milktea.service.PromotionService;
import com.phuclong.milktea.milktea.service.RestaurantService;
import com.phuclong.milktea.milktea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/drink")
public class AdminDrinkController {
    @Autowired
    private DrinkService drinkService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private PromotionService promotionService;

    @PostMapping
    public ResponseEntity<Drink> createDrink(@RequestBody CreateDrinkRequest req,
                                             @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        Drink savedDrink = drinkService.createDrink(req, req.getCategory(), restaurant);

        return new ResponseEntity<>(savedDrink, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteDrink(@PathVariable Long id,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        drinkService.deleteDrink(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Deleted drink successfully");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Drink> updateDrinkAvailabilityStatus(@PathVariable Long id,
                                             @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Drink drink = drinkService.updateAvailabilityStatus(id);

        drink = promotionService.getDrinkPromotion(drink.getId());

        return new ResponseEntity<>(drink, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/not-promotion")
    public ResponseEntity<List<Drink>> getDrinksRestaurantNotPromtion(
                                                               @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        List<Drink> drinks = drinkService.getDrinksRestaurantNotPromotion(restaurant);

        return new ResponseEntity<>(drinks, HttpStatus.CREATED);
    }
}

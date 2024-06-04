package com.phuclong.milktea.milktea.controller;

import com.phuclong.milktea.milktea.model.Category;
import com.phuclong.milktea.milktea.model.Drink;
import com.phuclong.milktea.milktea.model.Restaurant;
import com.phuclong.milktea.milktea.model.User;
import com.phuclong.milktea.milktea.service.CategoryService;
import com.phuclong.milktea.milktea.service.DrinkService;
import com.phuclong.milktea.milktea.service.RestaurantService;
import com.phuclong.milktea.milktea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class HomeController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private DrinkService drinkService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<String> HomeController(){
        return new ResponseEntity<>("Welcome to food delivery project", HttpStatus.OK);
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurant() throws Exception {

        List<Restaurant> restaurants = restaurantService.getAllRestaurant();

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> getRestaurantDetail(@PathVariable Long id) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


    @GetMapping("/drinks")
    public ResponseEntity<List<Drink>> getAllDrink() throws Exception {

        List<Drink> drinks = drinkService.getAllDrinks();

        return new ResponseEntity<>(drinks, HttpStatus.OK);
    }

    @GetMapping("/drinks/{id}")
    public ResponseEntity<Drink> getDrinkById(@PathVariable Long id) throws Exception {

        Drink drink = drinkService.findDrinkById(id);

        return new ResponseEntity<>(drink, HttpStatus.OK);
    }


}

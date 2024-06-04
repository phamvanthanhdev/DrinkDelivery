package com.phuclong.milktea.milktea.controller;

import com.phuclong.milktea.milktea.model.Restaurant;
import com.phuclong.milktea.milktea.model.User;
import com.phuclong.milktea.milktea.service.RestaurantService;
import com.phuclong.milktea.milktea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
public class HomeController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<String> HomeController(){
        return new ResponseEntity<>("Welcome to food delivery project", HttpStatus.OK);
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurant() throws Exception {

        List<Restaurant> restaurants = restaurantService.getAllRestaurant();

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }
}

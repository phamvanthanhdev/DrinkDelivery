package com.phuclong.milktea.milktea.controller;

import com.phuclong.milktea.milktea.model.Category;
import com.phuclong.milktea.milktea.model.Restaurant;
import com.phuclong.milktea.milktea.model.User;
import com.phuclong.milktea.milktea.service.CategoryService;
import com.phuclong.milktea.milktea.service.RestaurantService;
import com.phuclong.milktea.milktea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Category savedCategory = categoryService.createCategory(category.getName(), user.getId());

        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping("/admin/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(
                                                                @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        List<Category> categories = categoryService.findCategoryByRestaurantId(restaurant.getId());

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/category/restaurant/{id}")
    public ResponseEntity<List<Category>> getCategoryRestaurantId(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(id);
        List<Category> categories = categoryService.findCategoryByRestaurantId(restaurant.getId());

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

}

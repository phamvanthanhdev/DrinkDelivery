package com.phuclong.milktea.milktea.controller;

import com.phuclong.milktea.milktea.design.decorator.Promotion;
import com.phuclong.milktea.milktea.model.Drink;
import com.phuclong.milktea.milktea.model.Restaurant;
import com.phuclong.milktea.milktea.model.User;
import com.phuclong.milktea.milktea.request.AddPromotionRequest;
import com.phuclong.milktea.milktea.response.MessageResponse;
import com.phuclong.milktea.milktea.service.DiscountService;
import com.phuclong.milktea.milktea.service.PromotionService;
import com.phuclong.milktea.milktea.service.RestaurantService;
import com.phuclong.milktea.milktea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/discount")
public class DiscountController {
    @Autowired
    private UserService userService;

    @Autowired
    private DiscountService discountService;
    @Autowired
    private PromotionService promotionService;
    @PutMapping("/drink/{drinkId}")
    private ResponseEntity<Drink> updateDiscount(@PathVariable Long drinkId,
                                                            @RequestParam int percent,
                                                            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Drink drink = discountService.updateDiscount(drinkId, percent);
        drink = promotionService.getDrinkPromotion(drink.getId());
        return new ResponseEntity<>(drink, HttpStatus.OK);
    }

}

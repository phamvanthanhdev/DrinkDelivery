package com.phuclong.milktea.milktea.service;

import com.phuclong.milktea.milktea.design.decorator.*;
import com.phuclong.milktea.milktea.model.Drink;
import com.phuclong.milktea.milktea.model.Restaurant;
import com.phuclong.milktea.milktea.repository.PromotionRepository;
import com.phuclong.milktea.milktea.request.AddPromotionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImp implements PromotionService{
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private DrinkService drinkService;
    @Autowired
    private DiscountService discountService;

    @Override
    public Promotion createPromotion(AddPromotionRequest req, Restaurant restaurant) throws Exception {
//        System.out.println("PROMOTION " + req.toString());

        Promotion promotion = new Promotion();
        promotion.setDescription(req.getDescription());
        promotion.setName(req.getName());
        promotion.setPrice(req.getPrice());
        promotion.setRestaurant(restaurant);
        promotion.setStartDate(req.getStartDate());
        promotion.setEndDate(req.getEndDate());

        List<Drink> drinks = new ArrayList<>();
        for (Long drinkId: req.getDrinksId()) {
            Drink drink = drinkService.findDrinkById(drinkId);
            drinks.add(drink);
        }
        promotion.setDrinks(drinks);

        return promotionRepository.save(promotion);
    }

    @Override
    public Drink getDrinkPromotion(Long drinkId) throws Exception {
        Drink drink = drinkService.findDrinkById(drinkId);

        List<Promotion> promotions = promotionRepository.findByRestaurantId(drink.getRestaurant().getId());

        Promotion promotion = null;
        for (Promotion pro:promotions) {
            if(pro.getDrinks().contains(drink)){
                promotion = pro;
            }
        }

        DrinkDefault promotionProduct = null;
        DrinkDefault discountedProduct = null;
        DrinkDefault drinkDefault = new BasicDrink(drink.getId(), drink.getName(), drink.getDescription(), drink.getPrice(), drink.getDrinkCategory(), drink.getImages(), drink.isAvailable(), drink.getRestaurant(), drink.isVegetarian(), drink.isSeasonal(), drink.getIngredientsItems(), drink.getCreationDate());

        /*Discount discount = new Discount(1L,drink,0);*/
        Discount discount = discountService.findDiscountByDrinkId(drinkId);
        discountedProduct = new DiscountDecorator(drinkDefault, discount.getDiscount());

        if(promotion!=null) {
            // Thêm khuyến mãi
            promotionProduct = new PromotionDecorator(discountedProduct, promotion.getDescription(), promotion.getPrice());
        }

        if(promotionProduct != null) {
            drink.setPrice(promotionProduct.getPrice());
            drink.setDescription(promotionProduct.getDescription());
        } else if (discountedProduct != null) {
            drink.setPrice(discountedProduct.getPrice());
            drink.setDescription(discountedProduct.getDescription());
        }


        // In thông tin sản phẩm
        /*System.out.println("======== Decorator ==========");

        System.out.println("Description: " + discountedProduct.getDescription());
        System.out.println("Price: $" + discountedProduct.getPrice());

        System.out.println("Description: " + promotionProduct.getDescription());
        System.out.println("Price: $" + promotionProduct.getPrice());

        System.out.println(discountedProduct.toString());*/

        return drink;
    }

    @Override
    public List<Drink> getDrinksRestaurantPromotion(Restaurant restaurant) throws Exception {
        List<Drink> drinks = drinkService.getRestaurantsAllDrink(restaurant.getId());
        drinks = drinks.stream().map(drink -> {
            try {
                return getDrinkPromotion(drink.getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        return drinks;
    }


    @Override
    public Promotion findPromotionById(Long id) throws Exception {
        Optional<Promotion> otp = promotionRepository.findById(id);
        if(otp.isEmpty()){
            throw  new Exception("Promotion not found!!");
        }
        return otp.get();
    }

    @Override
    public List<Promotion> getPromotionsRestaurant(Long restaurantId) {
        List<Promotion> promotions = promotionRepository.findByRestaurantId(restaurantId);
        return promotions;
    }

    @Override
    public void deletePromotion(Long id) throws Exception {

    }

    @Override
    public Promotion updatePromotionStatus(Long id) throws Exception {
        return null;
    }


}

package com.phuclong.milktea.milktea.service;

import com.phuclong.milktea.milktea.design.decorator.*;
import com.phuclong.milktea.milktea.design.iterator.DrinkCollection;
import com.phuclong.milktea.milktea.design.iterator.Iterator;
import com.phuclong.milktea.milktea.model.Category;
import com.phuclong.milktea.milktea.model.Drink;
import com.phuclong.milktea.milktea.model.Restaurant;
import com.phuclong.milktea.milktea.repository.DrinkRepository;
import com.phuclong.milktea.milktea.request.CreateDrinkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DrinkServiceImp implements DrinkService{
    @Autowired
    private DrinkRepository drinkRepository;

    @Override
    public Drink createDrink(CreateDrinkRequest req, Category category, Restaurant restaurant) {
        Drink drink = new Drink();
        drink.setDrinkCategory(category);
        drink.setRestaurant(restaurant);
        drink.setDescription(req.getDescription());
        drink.setImages(req.getImages());
        drink.setName(req.getName());
        drink.setPrice(req.getPrice());
        drink.setIngredientsItems(req.getIngredientsItems());
        drink.setSeasonal(req.isSeasonal());
        drink.setVegetarian(req.isVegetarian());

        Drink savedDrink = drinkRepository.save(drink);
        restaurant.getDrinks().add(savedDrink);

        return savedDrink;
    }

    @Override
    public void deleteDrink(Long drinkId) throws Exception {
        Drink drink = findDrinkById(drinkId);
        drink.setRestaurant(null);
        drinkRepository.save(drink);
    }

    @Override
    public List<Drink> getRestaurantsDrink(Long restaurantId,
                                           boolean isVegetarian,
                                           boolean isNonveg,
                                           boolean isSeasonal,
                                           String drinkCategory) {
        List<Drink> drinks = drinkRepository.findByRestaurantId(restaurantId);
        List<Drink> drinksFilter = new ArrayList<>();

        //Iterator DesignPattern
        DrinkCollection drinkCollection = new DrinkCollection(drinks);
        for (Iterator iter = drinkCollection.getIterator(); iter.hasNext(); ){
            Drink drink = (Drink) iter.next();
            if(isVegetarian == drink.isVegetarian() && isSeasonal == drink.isSeasonal()){
                if(drinkCategory!=null) {
                    if(drinkCategory.trim().equals("")){
                        drinksFilter.add(drink);
                    }else if (drink.getDrinkCategory().getName().equals(drinkCategory)) {
                        drinksFilter.add(drink);
                    }
                }else{
                    drinksFilter.add(drink);
                }
            }
        }
        return drinksFilter;

        /*if(isVegetarian){
            drinks = filterByVegetarian(drinks, isVegetarian);
        }
        if(isNonveg){
            drinks = filterByNonveg(drinks, isNonveg);
        }
        if(isSeasonal){
            drinks = filterBySeasonal(drinks, isSeasonal);
        }
        if(drinks!=null && !drinkCategory.equals("")){
            drinks = filterByCategory(drinks, drinkCategory);
        }
        return drinks;*/

    }

    @Override
    public List<Drink> getRestaurantsAllDrink(Long restaurantId) {
        return drinkRepository.findByRestaurantId(restaurantId);
    }

    private List<Drink> filterByCategory(List<Drink> drinks, String drinkCategory) {
        return drinks.stream().
                filter(drink -> {
                    if(drink.getDrinkCategory() != null){
                        return drink.getDrinkCategory().getName().equals(drinkCategory);
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    private List<Drink> filterBySeasonal(List<Drink> drinks, boolean isSeasonal) {
        return drinks.stream().
                filter(drink -> drink.isSeasonal() == isSeasonal)
                .collect(Collectors.toList());
    }

    private List<Drink> filterByNonveg(List<Drink> drinks, boolean isNonveg) {
        return drinks.stream().
                filter(drink -> drink.isVegetarian() == false)
                .collect(Collectors.toList());
    }

    private List<Drink> filterByVegetarian(List<Drink> drinks, boolean isVegetarian) {
        return drinks.stream().
                filter(drink -> drink.isVegetarian() == isVegetarian)
                .collect(Collectors.toList());
    }

    @Override
    public List<Drink> searchDrink(String keyword) {
        return drinkRepository.searchDrink(keyword);
    }

    @Override
    public Drink findDrinkById(Long drinkId) throws Exception {
        Optional<Drink> optionalDrink = drinkRepository.findById(drinkId);
        if(optionalDrink.isEmpty()){
            throw new Exception("Drink not found");
        }

        Drink drink = optionalDrink.get();


        //
        /*Promotion promotion = new Promotion();
        promotion.setId(1L);
        promotion.setDescription("Tặng 1 ly nước mía");
        promotion.setPrice(5000);
        promotion.getDrinks().add(drink);

        DrinkDefault promotionProduct = null;
        DrinkDefault discountedProduct = null;
        DrinkDefault drinkDefault = new BasicDrink(drink.getId(), drink.getName(), drink.getDescription(), drink.getPrice(), drink.getDrinkCategory(), drink.getImages(), drink.isAvailable(), drink.getRestaurant(), drink.isVegetarian(), drink.isSeasonal(), drink.getIngredientsItems(), drink.getCreationDate());
        if(promotion.getDrinks().contains(drink)) {
            //Decorator design pattern used
            // Thêm khuyến mãi
            promotionProduct = new PromotionDecorator(drinkDefault, promotion.getDescription(), promotion.getPrice());
        }

        List<Discount> discounts = new ArrayList<>();
        discounts.add(new Discount(1L,drink,10));
        boolean check = false;
        for (Discount dis:discounts) {
            if(dis.getDrink() == drink){
                check = true;
            }
        }
        if(discounts.get(0).getDiscount() > 0 && check){
            if(promotionProduct!= null) {
                // Thêm giảm giá
                System.out.println("co KM: ");
                discountedProduct = new DiscountDecorator(promotionProduct, discounts.get(0).getDiscount());
            }else{
                System.out.println("ko KM: ");
                discountedProduct = new DiscountDecorator(drinkDefault, 0);
            }
        }

        // In thông tin sản phẩm
        System.out.println("======== Decorator ==========");

        System.out.println("Description: " + promotionProduct.getDescription());
        System.out.println("Price: $" + promotionProduct.getPrice());

        System.out.println("Description: " + discountedProduct.getDescription());
        System.out.println("Price: $" + discountedProduct.getPrice());

        System.out.println(discountedProduct.toString());

        drink.setPrice(discountedProduct.getPrice());
        drink.setDescription(discountedProduct.getDescription());*/

        return drink;
    }

    @Override
    public Drink updateAvailabilityStatus(Long drinkId) throws Exception {
        Drink drink = findDrinkById(drinkId);
        drink.setAvailable(!drink.isAvailable());
        return drinkRepository.save(drink);
    }

    @Override
    public List<Drink> getAllDrinks() {
        List<Drink> drinks = drinkRepository.findAll();
        return drinks;
    }

    @Override
    public List<Drink> getDrinksRestaurantNotPromotion(Restaurant restaurant) {
        return drinkRepository.findDrinksByRestaurantNotInPromotion(restaurant);
    }
}

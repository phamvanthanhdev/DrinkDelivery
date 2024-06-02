package com.phuclong.milktea.milktea.service;

import com.phuclong.milktea.milktea.model.Category;
import com.phuclong.milktea.milktea.model.Drink;
import com.phuclong.milktea.milktea.model.Restaurant;
import com.phuclong.milktea.milktea.repository.DrinkRepository;
import com.phuclong.milktea.milktea.request.CreateDrinkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        if(isVegetarian){
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

        return drinks;
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
        return optionalDrink.get();
    }

    @Override
    public Drink updateAvailabilityStatus(Long drinkId) throws Exception {
        Drink drink = findDrinkById(drinkId);
        drink.setAvailable(!drink.isAvailable());
        return drinkRepository.save(drink);
    }
}

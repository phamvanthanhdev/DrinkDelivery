package com.phuclong.milktea.milktea.service;

import com.phuclong.milktea.milktea.model.IngredientsCategory;
import com.phuclong.milktea.milktea.model.IngredientsItem;
import com.phuclong.milktea.milktea.model.Restaurant;
import com.phuclong.milktea.milktea.repository.IngredientCategoryRepository;
import com.phuclong.milktea.milktea.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientsService{
    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    private IngredientItemRepository ingredientItemRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientsCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientsCategory ingredientsCategory = new IngredientsCategory();
        ingredientsCategory.setRestaurant(restaurant);
        ingredientsCategory.setName(name);

        return ingredientCategoryRepository.save(ingredientsCategory);
    }

    @Override
    public IngredientsCategory findIngredientCategory(Long id) throws Exception {
        Optional<IngredientsCategory> otp = ingredientCategoryRepository.findById(id);
        if(otp.isEmpty()){
            throw new Exception("ingredient category not found");
        }

        return otp.get();
    }

    @Override
    public List<IngredientsCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory ingredientsCategory = findIngredientCategory(categoryId);

        IngredientsItem item = new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(ingredientsCategory);

        IngredientsItem ingredient = ingredientItemRepository.save(item);

        ingredientsCategory.getIngredients().add(ingredient);

        //ingredientCategoryRepository.save(ingredientsCategory);

        return ingredient;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> otp = ingredientItemRepository.findById(id);
        if(otp.isEmpty()){
            throw new Exception("Ingredient item not found");
        }
        IngredientsItem item = otp.get();
        item.setStoke(!item.isStoke());

        return ingredientItemRepository.save(item);
    }
}

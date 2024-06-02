package com.phuclong.milktea.milktea.service;

import com.phuclong.milktea.milktea.model.IngredientCategory;
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
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory ingredientsCategory = new IngredientCategory();
        ingredientsCategory.setRestaurant(restaurant);
        ingredientsCategory.setName(name);

        return ingredientCategoryRepository.save(ingredientsCategory);
    }

    @Override
    public IngredientCategory findIngredientCategory(Long id) throws Exception {
        Optional<IngredientCategory> otp = ingredientCategoryRepository.findById(id);
        if(otp.isEmpty()){
            throw new Exception("ingredient category not found");
        }

        return otp.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = findIngredientCategory(categoryId);

        IngredientsItem item = new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(ingredientCategory);

        IngredientsItem ingredient = ingredientItemRepository.save(item);

        ingredientCategory.getIngredients().add(ingredient);

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

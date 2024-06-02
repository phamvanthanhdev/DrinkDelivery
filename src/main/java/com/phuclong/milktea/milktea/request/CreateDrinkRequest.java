package com.phuclong.milktea.milktea.request;

import com.phuclong.milktea.milktea.model.Category;
import com.phuclong.milktea.milktea.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateDrinkRequest {
    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredientsItems;


}

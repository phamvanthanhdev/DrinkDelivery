package com.phuclong.milktea.milktea.design.decorator;

import com.phuclong.milktea.milktea.model.Category;
import com.phuclong.milktea.milktea.model.IngredientsItem;
import com.phuclong.milktea.milktea.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicDrink implements DrinkDefault{
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Category drinkCategory;
    private List<String> images;
    private boolean available;
    private Restaurant restaurant;
    private boolean isVegetarian;
    private boolean isSeasonal;
    private List<IngredientsItem> ingredientsItems = new ArrayList<>();
    private Date creationDate;


    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Long getPrice() {
        return price;
    }

}

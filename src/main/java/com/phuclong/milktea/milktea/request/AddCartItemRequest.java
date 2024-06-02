package com.phuclong.milktea.milktea.request;

import com.phuclong.milktea.milktea.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class AddCartItemRequest {
    private Long drinkId;
    private int quantity;
    private List<String> ingredients;
}

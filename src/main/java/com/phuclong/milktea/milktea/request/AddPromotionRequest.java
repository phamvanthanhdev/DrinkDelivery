package com.phuclong.milktea.milktea.request;

import com.phuclong.milktea.milktea.model.Drink;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddPromotionRequest {
    private String name;
    private String description;
    private int price;
    private List<Long> drinksId;
    private String startDate;
    private String endDate;
}
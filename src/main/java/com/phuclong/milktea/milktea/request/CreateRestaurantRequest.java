package com.phuclong.milktea.milktea.request;

import com.phuclong.milktea.milktea.model.Address;
import com.phuclong.milktea.milktea.model.ContactInfomation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateRestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInfomation contactInfomation;
    private String openingHours;
    private List<String> images;
}

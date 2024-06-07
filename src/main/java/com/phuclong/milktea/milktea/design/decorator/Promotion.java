package com.phuclong.milktea.milktea.design.decorator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phuclong.milktea.milktea.model.Drink;
import com.phuclong.milktea.milktea.model.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private int price;
    private String startDate;
    private String endDate;
    @OneToMany
    private List<Drink> drinks = new ArrayList<>();
    private boolean status = true;
    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;
}

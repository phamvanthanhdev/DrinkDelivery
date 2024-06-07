package com.phuclong.milktea.milktea.repository;

import com.phuclong.milktea.milktea.design.decorator.Promotion;
import com.phuclong.milktea.milktea.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByRestaurantId(Long restaurantId);
}

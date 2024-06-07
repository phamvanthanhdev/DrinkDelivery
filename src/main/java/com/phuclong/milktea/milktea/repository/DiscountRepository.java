package com.phuclong.milktea.milktea.repository;

import com.phuclong.milktea.milktea.design.decorator.Discount;
import com.phuclong.milktea.milktea.design.decorator.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Discount findByDrinkId(Long drinkId);
}

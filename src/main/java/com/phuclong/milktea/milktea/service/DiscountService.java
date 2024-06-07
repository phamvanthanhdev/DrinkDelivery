package com.phuclong.milktea.milktea.service;

import com.phuclong.milktea.milktea.design.decorator.Discount;
import com.phuclong.milktea.milktea.model.Drink;

public interface DiscountService {
    public Discount createDiscount(Discount discount);
    public Discount findDiscountById(Long id) throws Exception;
    public Drink updateDiscount(Long drinkId, int percent) throws Exception;
    public Discount findDiscountByDrinkId(Long drinkId) throws Exception;
}

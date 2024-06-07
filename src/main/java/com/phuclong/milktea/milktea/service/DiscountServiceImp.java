package com.phuclong.milktea.milktea.service;

import com.phuclong.milktea.milktea.design.decorator.Discount;
import com.phuclong.milktea.milktea.model.Drink;
import com.phuclong.milktea.milktea.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiscountServiceImp implements DiscountService{
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private DrinkService drinkService;

    @Override
    public Discount createDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    @Override
    public Discount findDiscountById(Long id) throws Exception {
        Optional<Discount> optionalDiscount = discountRepository.findById(id);
        if(optionalDiscount.isEmpty()){
            throw new Exception("Discount no found!");
        }
        return optionalDiscount.get();
    }

    @Override
    public Drink updateDiscount(Long drinkId, int percent) throws Exception {
        Discount discount = findDiscountByDrinkId(drinkId);
        discount.setDiscount(percent);
        discountRepository.save(discount);

        return drinkService.findDrinkById(drinkId);
    }

    @Override
    public Discount findDiscountByDrinkId(Long drinkId) throws Exception {
        Discount discount = discountRepository.findByDrinkId(drinkId);
        if(discount == null){
            discount = new Discount();
            discount.setDiscount(0);
            discount.setDrink(drinkService.findDrinkById(drinkId));
            createDiscount(discount);
        }
        return discount;
    }
}

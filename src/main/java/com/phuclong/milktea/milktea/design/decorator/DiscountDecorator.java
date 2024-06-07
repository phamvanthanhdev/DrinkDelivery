package com.phuclong.milktea.milktea.design.decorator;

public class DiscountDecorator extends DrinkDecorator{
    private int discountPercent;
    public DiscountDecorator(DrinkDefault drinkDefault, int discountPercent) {
        super(drinkDefault);
        this.discountPercent = discountPercent;
    }

    @Override
    public String getDescription() {
        return drinkDefault.getDescription() + ". Giảm giá " + discountPercent + " %";
    }

    @Override
    public Long getPrice() {
        return   drinkDefault.getPrice() - (((drinkDefault.getPrice() * discountPercent)/ 100));
    }
}

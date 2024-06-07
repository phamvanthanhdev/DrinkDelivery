package com.phuclong.milktea.milktea.design.decorator;

public class PromotionDecorator extends DrinkDecorator{
    private String promotionDescription;
    private int promotionAmount;

    public PromotionDecorator(DrinkDefault drinkDefault, String promotionDescription, int promotionAmount) {
        super(drinkDefault);
        this.promotionDescription = promotionDescription;
        this.promotionAmount = promotionAmount;
    }


    @Override
    public String getDescription() {
        return drinkDefault.getDescription() + ".Khuyến mãi: " + promotionDescription + ", trị giá " + promotionAmount;
    }

    @Override
    public Long getPrice() {
        return drinkDefault.getPrice();
    }
}

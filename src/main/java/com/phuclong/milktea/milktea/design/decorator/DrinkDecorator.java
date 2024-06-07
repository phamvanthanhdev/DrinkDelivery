package com.phuclong.milktea.milktea.design.decorator;

public abstract class DrinkDecorator implements DrinkDefault{
    protected DrinkDefault drinkDefault;

    public DrinkDecorator(DrinkDefault drinkDefault) {
        this.drinkDefault = drinkDefault;
    }

    @Override
    public String getDescription() {
        return drinkDefault.getDescription();
    }

    @Override
    public Long getPrice() {
        return drinkDefault.getPrice();
    }
}

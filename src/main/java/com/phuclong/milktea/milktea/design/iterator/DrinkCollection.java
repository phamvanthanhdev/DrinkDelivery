package com.phuclong.milktea.milktea.design.iterator;

import com.phuclong.milktea.milktea.model.Drink;

import java.util.List;

public class DrinkCollection implements Container{
    private List<Drink> drinks;

    public DrinkCollection(List<Drink> drinks) {
        this.drinks = drinks;
    }

    @Override
    public Iterator getIterator() {
        return new DrinkIterator();
    }

    private class DrinkIterator implements Iterator{
        int index;
        @Override
        public boolean hasNext() {
            if(index < drinks.size()){
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if(this.hasNext()){
                return drinks.get(index++);
            }
            return null;
        }

    }
}

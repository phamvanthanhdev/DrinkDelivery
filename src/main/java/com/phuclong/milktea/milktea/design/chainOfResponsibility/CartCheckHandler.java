package com.phuclong.milktea.milktea.design.chainOfResponsibility;

import com.phuclong.milktea.milktea.model.Address;
import com.phuclong.milktea.milktea.model.Cart;

import java.util.List;

public class CartCheckHandler extends OrderHandler{


    @Override
    public void handleOrder(Cart cart, Address address, List<Boolean> checkCart) {
        if (cart.getItems().size() > 0) {
            checkCart.add(true);
        } else {
            checkCart.add(false);
            return;
        }
        if (nextHandler != null) {
            nextHandler.handleOrder(cart, address, checkCart);
        }
    }
}

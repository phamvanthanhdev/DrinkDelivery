package com.phuclong.milktea.milktea.design.chainOfResponsibility;

import com.phuclong.milktea.milktea.model.Address;
import com.phuclong.milktea.milktea.model.Cart;

import java.util.List;

public class PaymentProcessCheckHandler extends OrderHandler{


    @Override
    public void handleOrder(Cart cart, Address address, List<Boolean> checkCart) {
        if (cart.getTotal() >= 0) {
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

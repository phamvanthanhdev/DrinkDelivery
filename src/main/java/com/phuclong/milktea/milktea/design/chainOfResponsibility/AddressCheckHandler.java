package com.phuclong.milktea.milktea.design.chainOfResponsibility;

import com.phuclong.milktea.milktea.model.Address;
import com.phuclong.milktea.milktea.model.Cart;

import java.util.List;

public class AddressCheckHandler extends OrderHandler{


    @Override
    public void handleOrder(Cart cart, Address address, List<Boolean> checkCart) {
        if (address.getStreetAddress() != null && address.getStateProvice() != null
                && address.getCity() != null && address.getCounty() != null) {
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

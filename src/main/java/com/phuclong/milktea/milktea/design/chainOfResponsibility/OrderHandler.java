package com.phuclong.milktea.milktea.design.chainOfResponsibility;

import com.phuclong.milktea.milktea.model.Address;
import com.phuclong.milktea.milktea.model.Cart;
import com.phuclong.milktea.milktea.model.Order;

import java.util.List;

public abstract class OrderHandler {
    protected OrderHandler nextHandler;

    public void setNextHandler(OrderHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleOrder(Cart cart, Address address, List<Boolean> checkCart);
}

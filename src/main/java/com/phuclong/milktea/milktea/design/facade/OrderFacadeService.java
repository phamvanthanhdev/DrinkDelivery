package com.phuclong.milktea.milktea.design.facade;

import com.phuclong.milktea.milktea.model.Order;
import com.phuclong.milktea.milktea.model.User;
import com.phuclong.milktea.milktea.request.OrderRequest;

public interface OrderFacadeService {
    public Order createOrder(OrderRequest req, User user) throws Exception;
    public Order updateOrder(Long orderId, String orderStatus) throws Exception;
    public void cancelOrder(Long orderId) throws Exception;
}

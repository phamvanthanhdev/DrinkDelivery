package com.phuclong.milktea.milktea.controller;

import com.phuclong.milktea.milktea.model.Order;
import com.phuclong.milktea.milktea.model.User;
import com.phuclong.milktea.milktea.request.OrderRequest;
import com.phuclong.milktea.milktea.service.OrderService;
import com.phuclong.milktea.milktea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;


    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(
            @PathVariable Long id,
            @RequestParam(required = false) String orderStatus) throws Exception {

        List<Order> orders = orderService.getRestaurantsOrder(id, orderStatus);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus) throws Exception {
        Order orders = orderService.updateOrder(id, orderStatus);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}

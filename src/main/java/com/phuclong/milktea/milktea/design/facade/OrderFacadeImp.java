package com.phuclong.milktea.milktea.design.facade;

import com.phuclong.milktea.milktea.design.chainOfResponsibility.AddressCheckHandler;
import com.phuclong.milktea.milktea.design.chainOfResponsibility.CartCheckHandler;
import com.phuclong.milktea.milktea.design.chainOfResponsibility.OrderHandler;
import com.phuclong.milktea.milktea.design.chainOfResponsibility.PaymentProcessCheckHandler;
import com.phuclong.milktea.milktea.model.*;
import com.phuclong.milktea.milktea.repository.AddressRepository;
import com.phuclong.milktea.milktea.repository.OrderItemRepository;
import com.phuclong.milktea.milktea.repository.OrderRepository;
import com.phuclong.milktea.milktea.repository.UserRepository;
import com.phuclong.milktea.milktea.request.OrderRequest;
import com.phuclong.milktea.milktea.service.CartService;
import com.phuclong.milktea.milktea.service.CartServiceImp;
import com.phuclong.milktea.milktea.service.RestaurantService;
import com.phuclong.milktea.milktea.serviceimp.RestaurantServiceImp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OrderFacadeImp implements OrderFacadeService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    private final RestaurantService restaurantService;
    private final CartService cartService;

    public OrderFacadeImp() {
        this.restaurantService = new RestaurantServiceImp();
        this.cartService = new CartServiceImp();
    }

    @Override
    public Order createOrder(OrderRequest req, User user) throws Exception {
        Address shippingAddress = req.getDeliveryAddress();
        Cart cart = cartService.findCartByUserId(user.getId());

        //Chain of responsibility
        OrderHandler cartCheckHandler = new CartCheckHandler();
        OrderHandler addressCheckHandler = new AddressCheckHandler();
        OrderHandler paymentCheckHandler = new PaymentProcessCheckHandler();

        List<Boolean> checking = new ArrayList<>();

        cartCheckHandler.setNextHandler(addressCheckHandler);
        addressCheckHandler.setNextHandler(paymentCheckHandler);

        cartCheckHandler.handleOrder(cart, shippingAddress, checking);

        for (int i = 0; i < checking.size(); i++) {
            if(!checking.get(i)){
                throw new Exception("Error processing order");
            }
        }


        Address savedAddress =addressRepository.save(shippingAddress);

        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());

        Order createOrder = new Order();
        createOrder.setCustomer(user);
        createOrder.setCreatedAt(new Date());
        createOrder.setOrderStatus("PENDING");
        createOrder.setDeliveryAddress(savedAddress);
        createOrder.setRestaurant(restaurant);

        //Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem:cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setDrink(cartItem.getDrink());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
        Long totalPrice = cartService.calculateCartTotals(cart);

        createOrder.setItems(orderItems);
        createOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(createOrder);
        restaurant.getOrders().add(savedOrder);

        return createOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERY")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")){
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }

        throw new Exception("Please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(order.getId());
    }

    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isEmpty()){
            throw new Exception("Order not found");
        }

        return orderOptional.get();
    }
}

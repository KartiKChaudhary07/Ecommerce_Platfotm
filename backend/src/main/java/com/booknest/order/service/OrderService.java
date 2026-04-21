package com.booknest.order.service;

import com.booknest.order.entity.Order;
import com.booknest.order.entity.OrderStatus;
import com.booknest.user.entity.User;

import java.util.List;

public interface OrderService {
    Order placeOrder(User user, String shippingAddress, String paymentMethod);
    Order getOrderById(Long id);
    List<Order> getUserOrders(User user);
    List<Order> getAllOrders();
    Order updateOrderStatus(Long orderId, OrderStatus status);
}

package com.booknest.order.controller;

import com.booknest.order.client.CartClient;
import com.booknest.order.entity.Order;
import com.booknest.order.entity.OrderItem;
import com.booknest.order.entity.OrderStatus;
import com.booknest.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartClient cartClient;

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam String shippingAddress,
            @RequestParam String paymentMethod) {
        
        List<Map<String, Object>> cartItems = cartClient.getCart(userId);
        if (cartItems.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map<String, Object> itemMap : cartItems) {
            Map<String, Object> bookMap = (Map<String, Object>) itemMap.get("book");
            Integer quantity = (Integer) itemMap.get("quantity");
            BigDecimal price = new BigDecimal(bookMap.get("price").toString());

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setBookId(Long.valueOf(bookMap.get("id").toString()));
            item.setQuantity(quantity);
            item.setPrice(price);
            items.add(item);

            total = total.add(price.multiply(BigDecimal.valueOf(quantity)));
        }

        order.setItems(items);
        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);
        cartClient.clearCart(userId);

        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getMyOrders(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(orderRepository.findByUserId(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(status);
            return ResponseEntity.ok(orderRepository.save(order));
        }).orElse(ResponseEntity.notFound().build());
    }
}

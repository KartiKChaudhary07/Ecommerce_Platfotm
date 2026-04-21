package com.booknest.cart.controller;

import com.booknest.cart.client.BookClient;
import com.booknest.cart.dto.BookDTO;
import com.booknest.cart.entity.CartItem;
import com.booknest.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookClient bookClient;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getCart(@RequestHeader("X-User-Id") Long userId) {
        List<CartItem> items = cartRepository.findByUserId(userId);
        List<Map<String, Object>> response = items.stream().map(item -> {
            BookDTO book = bookClient.getBookById(item.getBookId());
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("book", book);
            map.put("quantity", item.getQuantity());
            return map;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam Long bookId,
            @RequestParam Integer quantity) {
        CartItem cartItem = cartRepository.findByUserIdAndBookId(userId, bookId)
                .orElse(new CartItem(null, userId, bookId, 0));
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartRepository.save(cartItem);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        return cartRepository.findById(id).map(item -> {
            item.setQuantity(quantity);
            cartRepository.save(item);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long id) {
        cartRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestHeader("X-User-Id") Long userId) {
        cartRepository.deleteByUserId(userId);
        return ResponseEntity.ok().build();
    }
}

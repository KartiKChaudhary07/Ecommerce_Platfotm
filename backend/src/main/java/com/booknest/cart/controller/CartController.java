package com.booknest.cart.controller;

import com.booknest.cart.entity.CartItem;
import com.booknest.cart.service.CartService;
import com.booknest.security.UserDetailsImpl;
import com.booknest.user.entity.User;
import com.booknest.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).get();
        return ResponseEntity.ok(cartService.getCartItems(user));
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long bookId,
            @RequestParam Integer quantity) {
        User user = userRepository.findById(userDetails.getId()).get();
        return ResponseEntity.ok(cartService.addToCart(user, bookId, quantity));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CartItem> updateQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateQuantity(id, quantity));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id) {
        cartService.removeItem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).get();
        cartService.clearCart(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotal(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).get();
        return ResponseEntity.ok(cartService.calculateTotal(user));
    }
}

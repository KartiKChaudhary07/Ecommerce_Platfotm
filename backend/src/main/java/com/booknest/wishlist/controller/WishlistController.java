package com.booknest.wishlist.controller;

import com.booknest.security.UserDetailsImpl;
import com.booknest.user.entity.User;
import com.booknest.user.repository.UserRepository;
import com.booknest.wishlist.entity.WishlistItem;
import com.booknest.wishlist.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
@CrossOrigin(origins = "*")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<WishlistItem>> getWishlist(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).get();
        return ResponseEntity.ok(wishlistService.getWishlistItems(user));
    }

    @PostMapping("/add")
    public ResponseEntity<WishlistItem> addToWishlist(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long bookId) {
        User user = userRepository.findById(userDetails.getId()).get();
        return ResponseEntity.ok(wishlistService.addToWishlist(user, bookId));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Long id) {
        wishlistService.removeFromWishlist(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/move-to-cart/{id}")
    public ResponseEntity<Void> moveToCart(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id) {
        User user = userRepository.findById(userDetails.getId()).get();
        wishlistService.moveToCart(user, id);
        return ResponseEntity.ok().build();
    }
}

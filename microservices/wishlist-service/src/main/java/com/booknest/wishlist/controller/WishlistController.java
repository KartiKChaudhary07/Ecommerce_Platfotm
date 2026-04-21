package com.booknest.wishlist.controller;

import com.booknest.wishlist.entity.WishlistItem;
import com.booknest.wishlist.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistController {

    @Autowired
    private WishlistRepository wishlistRepository;

    @GetMapping
    public ResponseEntity<List<WishlistItem>> getWishlist(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(wishlistRepository.findByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<WishlistItem> addToWishlist(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam Long bookId) {
        if (wishlistRepository.findByUserIdAndBookId(userId, bookId).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        WishlistItem item = new WishlistItem(null, userId, bookId);
        return ResponseEntity.ok(wishlistRepository.save(item));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable Long id) {
        wishlistRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

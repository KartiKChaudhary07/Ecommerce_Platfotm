package com.booknest.wishlist.service;

import com.booknest.user.entity.User;
import com.booknest.wishlist.entity.WishlistItem;

import java.util.List;

public interface WishlistService {
    WishlistItem addToWishlist(User user, Long bookId);
    void removeFromWishlist(Long wishlistItemId);
    List<WishlistItem> getWishlistItems(User user);
    void moveToCart(User user, Long wishlistItemId);
}

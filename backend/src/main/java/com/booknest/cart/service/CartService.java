package com.booknest.cart.service;

import com.booknest.cart.entity.CartItem;
import com.booknest.user.entity.User;

import java.util.List;

public interface CartService {
    CartItem addToCart(User user, Long bookId, Integer quantity);
    CartItem updateQuantity(Long cartItemId, Integer quantity);
    void removeItem(Long cartItemId);
    List<CartItem> getCartItems(User user);
    void clearCart(User user);
    Double calculateTotal(User user);
}

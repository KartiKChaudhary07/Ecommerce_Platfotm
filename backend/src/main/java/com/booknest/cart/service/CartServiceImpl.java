package com.booknest.cart.service;

import com.booknest.book.entity.Book;
import com.booknest.book.service.BookService;
import com.booknest.cart.entity.CartItem;
import com.booknest.cart.repository.CartRepository;
import com.booknest.common.exception.ResourceNotFoundException;
import com.booknest.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookService bookService;

    @Override
    @Transactional
    public CartItem addToCart(User user, Long bookId, Integer quantity) {
        Book book = bookService.getBookById(bookId);
        
        return cartRepository.findByUserAndBookId(user, bookId)
                .map(item -> {
                    item.setQuantity(item.getQuantity() + quantity);
                    return cartRepository.save(item);
                })
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setUser(user);
                    newItem.setBook(book);
                    newItem.setQuantity(quantity);
                    return cartRepository.save(newItem);
                });
    }

    @Override
    public CartItem updateQuantity(Long cartItemId, Integer quantity) {
        CartItem item = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        item.setQuantity(quantity);
        return cartRepository.save(item);
    }

    @Override
    public void removeItem(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    @Override
    public List<CartItem> getCartItems(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void clearCart(User user) {
        cartRepository.deleteByUser(user);
    }

    @Override
    public Double calculateTotal(User user) {
        return getCartItems(user).stream()
                .mapToDouble(item -> item.getBook().getPrice().doubleValue() * item.getQuantity())
                .sum();
    }
}

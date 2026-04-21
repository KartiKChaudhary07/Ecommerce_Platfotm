package com.booknest.wishlist.service;

import com.booknest.book.entity.Book;
import com.booknest.book.service.BookService;
import com.booknest.cart.service.CartService;
import com.booknest.common.exception.ResourceNotFoundException;
import com.booknest.user.entity.User;
import com.booknest.wishlist.entity.WishlistItem;
import com.booknest.wishlist.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;

    @Override
    public WishlistItem addToWishlist(User user, Long bookId) {
        Book book = bookService.getBookById(bookId);
        
        return wishlistRepository.findByUserAndBookId(user, bookId)
                .orElseGet(() -> {
                    WishlistItem item = new WishlistItem();
                    item.setUser(user);
                    item.setBook(book);
                    return wishlistRepository.save(item);
                });
    }

    @Override
    public void removeFromWishlist(Long wishlistItemId) {
        wishlistRepository.deleteById(wishlistItemId);
    }

    @Override
    public List<WishlistItem> getWishlistItems(User user) {
        return wishlistRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void moveToCart(User user, Long wishlistItemId) {
        WishlistItem item = wishlistRepository.findById(wishlistItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found"));
        
        cartService.addToCart(user, item.getBook().getId(), 1);
        wishlistRepository.delete(item);
    }
}

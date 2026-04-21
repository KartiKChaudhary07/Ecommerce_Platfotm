package com.booknest.cart.repository;

import com.booknest.cart.entity.CartItem;
import com.booknest.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndBookId(User user, Long bookId);
    void deleteByUser(User user);
}

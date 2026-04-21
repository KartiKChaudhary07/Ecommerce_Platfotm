package com.booknest.review.controller;

import com.booknest.review.entity.Review;
import com.booknest.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Review>> getReviewsByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(reviewRepository.findByBookId(bookId));
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestHeader("X-User-Id") Long userId, @RequestBody Review review) {
        review.setUserId(userId);
        review.setReviewDate(LocalDateTime.now());
        return ResponseEntity.ok(reviewRepository.save(review));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        reviewRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

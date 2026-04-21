package com.booknest.review.controller;

import com.booknest.review.entity.Review;
import com.booknest.review.service.ReviewService;
import com.booknest.security.UserDetailsImpl;
import com.booknest.user.entity.User;
import com.booknest.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<Review> addReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long bookId,
            @RequestParam Integer rating,
            @RequestParam String comment) {
        User user = userRepository.findById(userDetails.getId()).get();
        return ResponseEntity.ok(reviewService.addReview(user, bookId, rating, comment));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Review>> getBookReviews(@PathVariable Long bookId) {
        return ResponseEntity.ok(reviewService.getBookReviews(bookId));
    }

    @GetMapping("/book/{bookId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long bookId) {
        return ResponseEntity.ok(reviewService.getAverageRating(bookId));
    }
}

package com.booknest.review.service;

import com.booknest.review.entity.Review;
import com.booknest.user.entity.User;

import java.util.List;

public interface ReviewService {
    Review addReview(User user, Long bookId, Integer rating, String comment);
    List<Review> getBookReviews(Long bookId);
    Double getAverageRating(Long bookId);
}

package com.booknest.review.service;

import com.booknest.book.entity.Book;
import com.booknest.book.service.BookService;
import com.booknest.review.entity.Review;
import com.booknest.review.repository.ReviewRepository;
import com.booknest.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookService bookService;

    @Override
    public Review addReview(User user, Long bookId, Integer rating, String comment) {
        Book book = bookService.getBookById(bookId);
        
        Review review = new Review();
        review.setUser(user);
        review.setBook(book);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewDate(LocalDateTime.now());
        
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getBookReviews(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    @Override
    public Double getAverageRating(Long bookId) {
        List<Review> reviews = getBookReviews(bookId);
        if (reviews.isEmpty()) return 0.0;
        
        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }
}

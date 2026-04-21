package com.booknest.book.service;

import com.booknest.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    Book createBook(Book book);
    Book updateBook(Long id, Book bookDetails);
    void deleteBook(Long id);
    Book getBookById(Long id);
    Page<Book> getAllBooks(Pageable pageable);
    Page<Book> searchBooks(String query, Pageable pageable);
    Page<Book> getBooksByGenre(String genre, Pageable pageable);
    List<Book> getFeaturedBooks();
}

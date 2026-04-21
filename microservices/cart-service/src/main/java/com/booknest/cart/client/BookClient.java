package com.booknest.cart.client;

import com.booknest.cart.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service", path = "/api/v1/books")
public interface BookClient {
    @GetMapping("/{id}")
    BookDTO getBookById(@PathVariable("id") Long id);
}

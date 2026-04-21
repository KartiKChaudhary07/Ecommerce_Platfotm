package com.booknest.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "book-service", path = "/api/v1/books")
public interface BookClient {
    @PutMapping("/{id}")
    void updateBook(@PathVariable("id") Long id, @RequestBody Map<String, Object> bookDetails);
}

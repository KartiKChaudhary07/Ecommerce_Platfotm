package com.booknest.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@FeignClient(name = "cart-service", path = "/api/v1/cart")
public interface CartClient {
    @GetMapping
    List<Map<String, Object>> getCart(@RequestHeader("X-User-Id") Long userId);

    @DeleteMapping("/clear")
    void clearCart(@RequestHeader("X-User-Id") Long userId);
}

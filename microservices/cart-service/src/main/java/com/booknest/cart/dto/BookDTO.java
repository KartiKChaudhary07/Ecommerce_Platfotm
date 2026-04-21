package com.booknest.cart.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private String imageUrl;
}

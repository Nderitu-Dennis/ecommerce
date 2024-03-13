package com.nderitu.ecommerce.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data

public class ProductDto {

    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    private byte[] byteImg;

    private Long categoryId;

    private String categoryName;

    private MultipartFile img;

    private Long quantity;
}

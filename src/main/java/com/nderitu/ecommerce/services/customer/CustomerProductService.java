package com.nderitu.ecommerce.services.customer;

import com.nderitu.ecommerce.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {

    List<ProductDto> searchProductByTitle(String title);

    List<ProductDto> getAllProducts();
}

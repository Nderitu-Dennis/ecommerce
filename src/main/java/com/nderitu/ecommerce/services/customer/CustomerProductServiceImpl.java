package com.nderitu.ecommerce.services.customer;

import com.nderitu.ecommerce.dto.ProductDto;
import com.nderitu.ecommerce.entity.Product;
import com.nderitu.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

//creating a dashboard page for customers to search and view all products by name
public class CustomerProductServiceImpl implements CustomerProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
//         the stmnt above maps list of product into product dto
    }

    //    method for getting a product by name/searching
    public List<ProductDto> searchProductByTitle(String name){
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());

    }
}

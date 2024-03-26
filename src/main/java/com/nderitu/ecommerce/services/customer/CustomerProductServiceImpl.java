package com.nderitu.ecommerce.services.customer;

import com.nderitu.ecommerce.dto.ProductDetailDto;
import com.nderitu.ecommerce.dto.ProductDto;
import com.nderitu.ecommerce.entity.Product;
import com.nderitu.ecommerce.repository.FAQRepository;
import com.nderitu.ecommerce.repository.ProductRepository;
import com.nderitu.ecommerce.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

//creating a dashboard page for customers to search and view all products by name
public class CustomerProductServiceImpl implements CustomerProductService {

    private final ProductRepository productRepository;
    private final FAQRepository faqRepository;
    private final ReviewRepository reviewRepository;


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

    public ProductDetailDto getProductDetailById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {


        }

        return null;
    }
}

package com.nderitu.ecommerce.repository;

import com.nderitu.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long> {
//    searching a product by name

    List<Product> findAllByNameContaining(String title);
}

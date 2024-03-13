package com.nderitu.ecommerce.services.admin.adminproduct;

import com.nderitu.ecommerce.dto.ProductDto;
import com.nderitu.ecommerce.entity.Category;
import com.nderitu.ecommerce.entity.Product;
import com.nderitu.ecommerce.repository.CategoryRepository;
import com.nderitu.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

//    add product method

  public ProductDto addProduct(ProductDto productDto) throws IOException {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
       product.setImg(productDto.getImg().getBytes());  //exception added to the method signature to use this getBytes

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();  //todo meaning of this line

        product.setCategory(category);  //set the category in the product

        return productRepository.save(product).getDto();
    }



//    method for getting all products

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
//         the stmnt above maps list of product into product dto
    }

    //    method for getting a product by name/searching
    public List<ProductDto> getAllProductByName(String name) {
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());

    }

    //method to delete a product
    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;

    }

    public ProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.map(Product::getDto).orElse(null);
    }

    //    method to update product details
    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());

    /*    if (optionalProduct.isPresent() && optionalCategory.isPresent()) {
            Product product = optionalProduct.get();

            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setCategory(optionalCategory.get());

           if (productDto.getImg() != null) {
                product.setImg(productDto.getImg().getBytes());
            }
            return productRepository.save(product).getDto();

        } else {
            return null;
        }*/
        return productDto;
    }

}




package com.nderitu.ecommerce.controller.admin;


import com.nderitu.ecommerce.dto.ProductDto;
import com.nderitu.ecommerce.services.admin.adminproduct.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor

public class AdminProductController {

    private final AdminProductService adminProductService;

//    code for the endpoint
    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto)
    throws IOException {
     /*   @ModelAttribute is used instead of request body cz we want to accept the
        MultiPart file which is an image*/

        ProductDto productDto1 = adminProductService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);

//        add product API is complete
    }
//getting all products
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDtos = adminProductService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }

    //searching a product by name
    @GetMapping("/search/{name}")  //name is a path variable
    public ResponseEntity<List<ProductDto>> getAllProductByName(@PathVariable String name){
        List<ProductDto> productDtos = adminProductService.getAllProductByName(name);
        return ResponseEntity.ok(productDtos);
    }

    //endpoint for the delete method
    public ResponseEntity<Void> deleteProduct (@PathVariable Long productId){
        boolean deleted = adminProductService.deleteProduct(productId);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

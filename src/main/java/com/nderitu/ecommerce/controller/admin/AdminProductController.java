package com.nderitu.ecommerce.controller.admin;


import com.nderitu.ecommerce.dto.FAQDto;
import com.nderitu.ecommerce.dto.ProductDto;
import com.nderitu.ecommerce.services.admin.adminproduct.AdminProductService;
import com.nderitu.ecommerce.services.admin.faq.FAQService;
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
    private final FAQService faqService; //injecting FAQService

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
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct (@PathVariable Long productId){
        boolean deleted = adminProductService.deleteProduct(productId);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

//    endpoint for post FAQ
    @PostMapping("/faq/{productId}")
    public ResponseEntity<FAQDto> postFAQ (@PathVariable Long productId, @RequestBody FAQDto faqDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(faqService.postFAQ(productId,faqDto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        ProductDto productDto = adminProductService.getProductById(productId);
        if(productDto !=null){
            return ResponseEntity.ok(productDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

   @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct (@PathVariable Long productId, @ModelAttribute
                                                     ProductDto productDto) throws IOException{
        ProductDto updatedProduct = adminProductService.updateProduct(productId,productDto);
        if(updatedProduct != null){
            return ResponseEntity.ok(updatedProduct);
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}

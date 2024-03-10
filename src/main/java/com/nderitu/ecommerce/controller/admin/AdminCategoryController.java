package com.nderitu.ecommerce.controller.admin;

import com.nderitu.ecommerce.dto.CategoryDto;
import com.nderitu.ecommerce.entity.Category;
import com.nderitu.ecommerce.services.admin.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor

public class AdminCategoryController {

//    object for categoryService

    private final CategoryService categoryService;

//    writing the API

    @PostMapping("/categories")
    public ResponseEntity<Category> createcategory(@RequestBody CategoryDto categoryDto){
        Category category = categoryService.createcategory(categoryDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);

    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}

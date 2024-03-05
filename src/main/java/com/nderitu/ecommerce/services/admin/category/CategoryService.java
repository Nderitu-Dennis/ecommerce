package com.nderitu.ecommerce.services.admin.category;

import com.nderitu.ecommerce.dto.CategoryDto;
import com.nderitu.ecommerce.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createcategory (CategoryDto categoryDto);

    List<Category> getAllCategories();
}

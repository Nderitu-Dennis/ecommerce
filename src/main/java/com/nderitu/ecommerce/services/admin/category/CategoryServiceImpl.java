package com.nderitu.ecommerce.services.admin.category;

import com.nderitu.ecommerce.dto.CategoryDto;
import com.nderitu.ecommerce.entity.Category;
import com.nderitu.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor  //automatically generates a constructor for a class using only fields marked with final or @NonNull
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    //method to create a category

    public Category createcategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return categoryRepository.  save(category);
    }

//    API to get all the categories
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }


}

package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private List<Category> categories = new ArrayList<>();
    private Long nextCategoryId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextCategoryId++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        // To support various error codes, We will use HTTP Response Entity to throw various exceptions
        // and the controller/api layer will try and catch those.
        Category category = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));

        categories.remove(category);

        return "Category with id " +category.getCategoryId()+" deleted successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Optional<Category> optionalCategory = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();

        if(optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());

            return existingCategory;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found");
        }
    }
}

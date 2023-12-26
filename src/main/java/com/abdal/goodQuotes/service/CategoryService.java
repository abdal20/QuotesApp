package com.abdal.goodQuotes.service;

import com.abdal.goodQuotes.entity.Category;
import com.abdal.goodQuotes.entity.Quotes;
import com.abdal.goodQuotes.repo.CategoryRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepository) {
        this.categoryRepo = categoryRepository;
    }

    // Other service methods for category management

    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElse(null);
    }

    public Category saveCategory(Category category) {
        return categoryRepo.save(category);
    }

    // Method to add a quote to a category
    public Category addQuoteToCategory(Long categoryId, Quotes quote) {
        Category category = getCategoryById(categoryId);

        if (category != null) {
            category.getQuotes().add(quote);
            return saveCategory(category);
        }

        return null;
    }
}

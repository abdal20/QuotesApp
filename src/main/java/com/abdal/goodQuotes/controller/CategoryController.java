package com.abdal.goodQuotes.controller;

import com.abdal.goodQuotes.entity.Category;
import com.abdal.goodQuotes.entity.Quotes;
import com.abdal.goodQuotes.repo.CategoryRepo;
import com.abdal.goodQuotes.repo.QuotesRepository;
import com.abdal.goodQuotes.service.CategoryService;
import com.abdal.goodQuotes.service.QuotesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@CrossOrigin("http://localhost:3000")
public class CategoryController {

    private final CategoryRepo categoryRepo;
    private final QuotesRepository quoteRepo;
    private final CategoryService categoryService;
    private final QuotesService quoteService; // Make this final as well

    @Autowired
    public CategoryController(CategoryRepo categoryRepo, QuotesRepository quoteRepo, CategoryService categoryService, QuotesService quoteService) {
        this.categoryRepo = categoryRepo;
        this.quoteRepo = quoteRepo;
        this.categoryService = categoryService;
        this.quoteService = quoteService;
    }
    @GetMapping("/{categoryId}/quotes")
    public ResponseEntity<List<Quotes>> getQuotesByCategory(@PathVariable Long categoryId) {
        Optional<Category> categoryOptional = categoryRepo.findById(categoryId);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            List<Quotes> quotes = category.getQuotes(); // Assuming getQuotes() returns a list of quotes
            return ResponseEntity.ok(quotes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category savedCategory = categoryRepo.save(category);
        return ResponseEntity.ok(savedCategory);
    }

    @PutMapping("/{categoryId}/quotes/{quoteId}")
    public ResponseEntity<Void> addQuoteToCategory(@PathVariable Long categoryId, @PathVariable int quoteId) {
        Category category = categoryService.getCategoryById(categoryId);
        Quotes quote = quoteService.getQuoteById(quoteId);

        if (category == null || quote == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        category.getQuotes().add(quote);
        categoryService.saveCategory(category);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{categoryId}/addQuote")
    public ResponseEntity<?> addQuoteToCategory(@PathVariable Long categoryId, @RequestBody Quotes quote) {
        Optional<Category> categoryOptional = categoryRepo.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.getQuotes().add(quote);
            quoteRepo.save(quote);
            return ResponseEntity.ok("Quote added to category successfully.");
        } else {
            return ResponseEntity.badRequest().body("Category not found");
        }
    }
}

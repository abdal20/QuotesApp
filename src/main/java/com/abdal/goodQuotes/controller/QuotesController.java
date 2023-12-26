package com.abdal.goodQuotes.controller;

import com.abdal.goodQuotes.entity.Category;
import com.abdal.goodQuotes.entity.Quotes;
import com.abdal.goodQuotes.repo.CategoryRepo;
import com.abdal.goodQuotes.service.CategoryService;
import com.abdal.goodQuotes.service.QuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quotes")
@CrossOrigin("http://localhost:3000")

public class QuotesController {


    private final QuotesService quotesService;
    private final CategoryService categoryService;
    private final CategoryRepo categoryRepo;

    @Autowired
    public QuotesController(QuotesService quotesService, CategoryService categoryService, CategoryRepo categoryRepo) {
        this.quotesService = quotesService;
        this.categoryService = categoryService;
        this.categoryRepo = categoryRepo;
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
    public ResponseEntity<List<Quotes>> getAllQuotes() {
        List<Quotes> quotesList = quotesService.getAllQuotes();
        return new ResponseEntity<>(quotesList, HttpStatus.OK);
    }
    @PutMapping("/{quoteId}/category/{categoryId}")
    public ResponseEntity<Quotes> addQuoteToCategory(
            @PathVariable int quoteId,
            @PathVariable Long categoryId) {
        Quotes quote = quotesService.getQuoteById(quoteId);
        Category category = categoryService.getCategoryById(categoryId);

        if (quote != null && category != null) {
            category.getQuotes().add(quote);
            categoryService.saveCategory(category);

            return new ResponseEntity<>(quote, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quotes> getQuoteById(@PathVariable int id) {
        Quotes quote = quotesService.getQuoteById(id);
        if (quote != null) {
            return new ResponseEntity<>(quote, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Quotes> addQuote(@RequestBody Quotes quote) {
        Quotes newQuote = quotesService.addQuote(quote);
        return new ResponseEntity<>(newQuote, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quotes> updateQuote(@PathVariable int id, @RequestBody Quotes quote) {
        Quotes updatedQuote = quotesService.updateQuote(id, quote);
        if (updatedQuote != null) {
            return new ResponseEntity<>(updatedQuote, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable int id) {
        if (quotesService.deleteQuote(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }
}

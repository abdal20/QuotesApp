package com.abdal.goodQuotes.service;

import com.abdal.goodQuotes.entity.Category;
import com.abdal.goodQuotes.entity.Quotes;
import com.abdal.goodQuotes.repo.QuotesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuotesService {

    private final QuotesRepository quotesRepository;

    @Autowired
    public QuotesService(QuotesRepository quotesRepository) {
        this.quotesRepository = quotesRepository;
    }

    public List<Quotes> getAllQuotes() {
        return quotesRepository.findAll();
    }

    public Quotes getQuoteById(int id) {
        Optional<Quotes> optionalQuote = quotesRepository.findById(id);
        return optionalQuote.orElse(null);
    }

    public Quotes addQuote(Quotes quote) {
        return quotesRepository.save(quote);
    }

    public Quotes updateQuote(int id, Quotes newQuote) {
        Optional<Quotes> optionalQuote = quotesRepository.findById(id);
        if (optionalQuote.isPresent()) {
            Quotes existingQuote = optionalQuote.get();
            existingQuote.setQtext(newQuote.getQtext());
            existingQuote.setCategories(newQuote.getCategories());
            existingQuote.setAuthor(newQuote.getAuthor());
            return quotesRepository.save(existingQuote);
        }
        return null;
    }

    public boolean deleteQuote(int id) {
        Optional<Quotes> optionalQuote = quotesRepository.findById(id);
        if (optionalQuote.isPresent()) {
            quotesRepository.deleteById(id);
            return true;
        }
        return false;
    }

	public void saveCategory(Category category) {
		
	}
}

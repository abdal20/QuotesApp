package com.abdal.goodQuotes.repo;

import com.abdal.goodQuotes.entity.Quotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotesRepository extends JpaRepository<Quotes, Integer> {
    // You can add custom query methods here if needed
}

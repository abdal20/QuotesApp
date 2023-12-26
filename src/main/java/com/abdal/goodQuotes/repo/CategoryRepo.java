package com.abdal.goodQuotes.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.abdal.goodQuotes.entity.Category;
import com.abdal.goodQuotes.entity.Quotes;

public interface CategoryRepo extends JpaRepository<Category, Long> {



    // Add custom queries if needed
}

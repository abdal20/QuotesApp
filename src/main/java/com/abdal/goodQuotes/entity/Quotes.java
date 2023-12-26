package com.abdal.goodQuotes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
public class Quotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String qtext;
    private String author;

    @ManyToMany
    @JoinTable(
        name = "category_quotes",
        joinColumns = @JoinColumn(name = "quotes_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
}

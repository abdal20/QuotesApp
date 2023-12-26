package com.abdal.goodQuotes.entity;

import lombok.Getter; 
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;


@Getter
@Setter
@Entity
public class Category {

	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;
	    @ManyToMany
	    @JoinTable(
	        name = "category_quotes", // Name of the join table
	        joinColumns = @JoinColumn(name = "category_id"), // Foreign key in the join table referring to Category
	        inverseJoinColumns = @JoinColumn(name = "quotes_id")) // Foreign key in the join table referring to Quotes
	    private List<Quotes> quotes;
				}


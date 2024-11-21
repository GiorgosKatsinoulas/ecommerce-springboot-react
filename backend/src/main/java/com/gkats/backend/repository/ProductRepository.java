package com.gkats.backend.repository;

import com.gkats.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can define custom queries here if needed
    List<Product> findByNameContaining(String name);

    //Retrieve all products
    List<Product> findAll();

    //Retrieve products by category
    List<Product> findByCategoryIgnoringCase(String category);

    //Retrieve products by price range
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // Find products with a name that contains the given string (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Retrieve category list
    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findDistinctCategory();
}
package com.gkats.backend.services;


import com.gkats.backend.model.Product;
import com.gkats.backend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Product service.
 */
@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;


    /**
     * Instantiates the Product service.
     *
     * @param productRepository the productRepository service
     */
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Get products list.
     *
     * @return the list of products
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * Get product by id.
     *
     * @param id the id
     * @return the product
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found for ID: " + id));
    }

    /**
     * Get products by category.
     *
     * @param category the category
     * @return the list of products
     */
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoringCase(category);
    }

    /**
     * Get products by price range.
     *
     * @param minPrice the min price
     * @param maxPrice the max price
     * @return the list of products
     */
    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Search products by name.
     *
     * @param name the name
     * @return the list of products
     */
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Get category list.
     *
     * @return the list of categories
     */
    public List<String> getCategories() {
        return productRepository.findDistinctCategory();
    }

}

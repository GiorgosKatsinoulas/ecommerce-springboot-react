package com.gkats.backend.services;


import com.gkats.backend.model.Product;
import com.gkats.backend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Save product.
     *
     * @param product the product
     * @return the product
     */
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Delete product by id.
     *
     * @param productId the product id
     */
    @Transactional
    public void deleteProduct(long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteProductById(productId);
        } else {
            throw new EntityNotFoundException("Product not found with ID: " + productId);
        }
    }

    /**
     * Update product.
     *
     * @param product the product
     * @return the product
     */
    public Product updateProduct(Long productId, Product product) {
        // Retrieve existing product by ID
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found for ID: " + productId));

        // Update fields only if the new values are not null
        if (product.getName() != null) existingProduct.setName(product.getName());
        if (product.getDescription() != null) existingProduct.setDescription(product.getDescription());
        if (product.getPrice() != null) existingProduct.setPrice(product.getPrice());
        if (product.getCategory() != null) existingProduct.setCategory(product.getCategory());
        if (product.getImageurl() != null) existingProduct.setImageurl(product.getImageurl());
        if (product.getAvailable() != null) existingProduct.setAvailable(product.getAvailable());
        if (product.getSKU() != null) existingProduct.setSKU(product.getSKU());
        if (product.getDimensions() != null) existingProduct.setDimensions(product.getDimensions());
        if (product.getColor() != null) existingProduct.setColor(product.getColor());

        // Save the updated product
        return productRepository.save(existingProduct);
    }



}

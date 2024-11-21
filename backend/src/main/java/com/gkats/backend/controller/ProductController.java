package com.gkats.backend.controller;


import com.gkats.backend.model.Product;
import com.gkats.backend.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Product controller.
 */
@RestController
@RequestMapping("api/products")
@Slf4j
public class ProductController {

    // Dependency injection
    private final ProductService productService;

    // Constructor
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    /**
     * Get products list.
     *
     * @return the list of products
     */
    @GetMapping("/getProducts")
    public ResponseEntity<List<Product>> getProducts() {
        log.info("Get products list...");
        List<Product> products = productService.getProducts();
        if(products.isEmpty()){
            return ResponseEntity.notFound().build();   // Returns HTTP 404 if no products are found
        }
        return ResponseEntity.ok(products); // Returns HTTP 200 with the product list
    }

    /**
     * Get product by id.
     *
     * @param id the id
     * @return the product
     */
        @GetMapping("/getProductById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("Get product by id...");
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product); // Returns HTTP 200 with the product
        } catch (Exception e) {
            log.error("Product not found for ID: {}", id, e);
            return ResponseEntity.notFound().build(); // Returns HTTP 404 if the product is not found
        }
    }

    /**
     * Get products by category.
     *
     * @param category the category
     * @return the list of products
     */
    @GetMapping("/getProductsByCategory/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        log.info("Get products by category...");
        List<Product> products = productService.getProductsByCategory(category);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build(); // Returns HTTP 404 if no products are found
        }
        return ResponseEntity.ok(products); // Returns HTTP 200 with the product list
    }

    /**
     * Get products by price range.
     *
     * @param minPrice the min price
     * @param maxPrice the max price
     * @return the list of products
     */
    @GetMapping("/getProductsByPriceRange/{minPrice}/{maxPrice}")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@PathVariable Double minPrice, @PathVariable Double maxPrice) {
        log.info("Get products by price range...");
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build(); // Returns HTTP 404 if no products are found
        }
        return ResponseEntity.ok(products); // Returns HTTP 200 with the product list
    }

    /**
     * Search products by name.
     *
     * @param name the name
     * @return the list of products
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        log.info("Search products by name...");
        List<Product> products = productService.searchProductsByName(name);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build(); // Returns HTTP 404 if no products are found
        }
        return ResponseEntity.ok(products); // Returns HTTP 200 with the product list
    }

    /**
     * Get categories list.
     *
     * @return the list of categories
     */
    @GetMapping("/getCategories")
    public ResponseEntity<List<String>> getCategories() {
        log.info("Get categories list...");
        List<String> categories = productService.getCategories();
        if(categories.isEmpty()) {
            return ResponseEntity.notFound().build();   // Returns HTTP 404 if no categories are found
        }
        return ResponseEntity.ok(categories); // Returns HTTP 200 with the category list
    }


    /**
     * Add product.
     *
     * @param product the product
     * @return the product
     */
    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        log.info("Add product...");
        Product savedProduct = productService.addProduct(product);
        if(savedProduct == null) {
            return ResponseEntity.internalServerError().build();   // Returns HTTP 500 if the product is not saved
        }
        return ResponseEntity.ok(savedProduct); // Returns HTTP 200 with the saved product
    }

    /**
     * Update product.
     *
     * @param id the id
     * @param product the product
     * @return the product
     */
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            log.info("Updating product with ID: {}", id);
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct); // Return HTTP 200 OK with updated product
        } catch (EntityNotFoundException e) {
            log.error("Product not found for ID: {}", id);
            return ResponseEntity.notFound().build(); // Return HTTP 404 Not Found if product doesn't exist
        } catch (Exception e) {
            log.error("Error updating product with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return HTTP 500 for other errors
        }
    }

        /**
         * Delete product by id.
         *
         * @param productId the product id
         */
        @DeleteMapping("/deleteProduct/{productId}")
        public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
            try {
                productService.deleteProduct(productId);
                return ResponseEntity.ok("Product deleted successfully.");
            } catch (EntityNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting the product.");
            }
        }


}

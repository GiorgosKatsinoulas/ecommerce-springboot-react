package com.gkats.backend.controller;


import com.gkats.backend.model.Product;
import com.gkats.backend.services.ProductService;
import com.gkats.backend.utils.ApiMessages;
import com.gkats.backend.utils.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<Product>>> getProducts() {
        log.info("Get products list...");
        try {
            List<Product> products = productService.getProducts();
            return ResponseEntity.ok(ApiResponse.success(
                    HttpStatus.OK.value(),
                    ApiMessages.SUCCESS,
                    products)); // Returns HTTP 200 with the product list
        } catch (Exception e) {
            log.error("Error getting products", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            ApiMessages.INTERNAL_ERROR,
                            e.getMessage())); // Returns HTTP 500 if an error occurs
        }
    }

    /**
     * Get product by id.
     *
     * @param id the id
     * @return the product
     */
    @GetMapping("/getProductById/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        log.info("Get product by id...");
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(ApiResponse.success(
                    HttpStatus.OK.value(),
                    ApiMessages.SUCCESS,
                    product)); // Returns HTTP 200 with the product
        } catch (Exception e) {
            log.error("Product not found for ID: {}", id, e);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(
                            HttpStatus.NOT_FOUND.value(),
                            ApiMessages.ITEM_NOT_FOUND,
                            e.getMessage())); // Returns HTTP 404 Not Found if product doesn't exist
        }
    }

    /**
     * Get products by category.
     *
     * @param category the category
     * @return the list of products
     */
    @GetMapping("/getProductsByCategory/{category}")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByCategory(@PathVariable String category) {
        log.info("Get products by category...");
        try {
            List<Product> products = productService.getProductsByCategory(category);
            return ResponseEntity.ok(ApiResponse.success(
                    HttpStatus.OK.value(),
                    ApiMessages.SUCCESS,
                    products)); // Returns HTTP 200 with the product list
        } catch (Exception e) {
            log.error("Error getting products by category: {}", category, e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            ApiMessages.INTERNAL_ERROR,
                            e.getMessage())); // Returns HTTP 500 if an error occurs
        }
    }

    /**
     * Get products by price range.
     *
     * @param minPrice the min price
     * @param maxPrice the max price
     * @return the list of products
     */
    @GetMapping("/getProductsByPriceRange/{minPrice}/{maxPrice}")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByPriceRange(@PathVariable Double minPrice, @PathVariable Double maxPrice) {
        log.info("Get products by price range...");
        try {
            List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
            return ResponseEntity.ok(ApiResponse.success(
                    HttpStatus.OK.value(),
                    ApiMessages.SUCCESS,
                    products)); // Returns HTTP 200 with the product list
        } catch (Exception e) {
            log.error("Error getting products by price range", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            ApiMessages.INTERNAL_ERROR,
                            e.getMessage())); // Returns HTTP 500 if an error occurs
        }
    }

    /**
     * Search products by name.
     *
     * @param name the name
     * @return the list of products
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Product>>> searchProducts(@RequestParam String name) {
        log.info("Search products by name...");
        try {
            List<Product> products = productService.searchProductsByName(name);
            return ResponseEntity.ok(ApiResponse.success(
                    HttpStatus.OK.value(),
                    ApiMessages.SUCCESS,
                    products));// Returns HTTP 200 with the product list
        } catch (Exception e) {
            log.error("Error searching products by name", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            ApiMessages.INTERNAL_ERROR,
                            e.getMessage())); // Returns HTTP 500 if an error occurs
        }
    }

    /**
     * Get categories list.
     *
     * @return the list of categories
     */
    @GetMapping("/getCategories")
    public ResponseEntity<ApiResponse<List<String>>> getCategories() {
        log.info("Get categories list...");
        try {
            List<String> categories = productService.getCategories();
            return ResponseEntity.ok(ApiResponse.success(
                    HttpStatus.OK.value(),
                    ApiMessages.SUCCESS,
                    categories)); // Returns HTTP 200 with the category list
        } catch (Exception e) {
            log.error("Error getting categories", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            ApiMessages.INTERNAL_ERROR,
                            e.getMessage())); // Returns HTTP 500 if an error occurs
        }
    }

    /**
     * Add product.
     *
     * @param product the product
     * @return the product
     */
    @PostMapping("/addProduct")
    public ResponseEntity<ApiResponse<Product>> addProduct(@RequestBody Product product) {
        log.info("Add product...");
        try {
            Product savedProduct = productService.addProduct(product);
            return ResponseEntity.ok(ApiResponse.success(
                    HttpStatus.CREATED.value(),
                    ApiMessages.SUCCESS,
                    savedProduct)); // Returns HTTP 201 with the saved product
        } catch (Exception e) {
            log.error("Error adding product", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            ApiMessages.INTERNAL_ERROR,
                            e.getMessage())); // Returns HTTP 500 if an error occurs
        }
    }

    /**
     * Update product.
     *
     * @param id      the id
     * @param product the product
     * @return the product
     */
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            log.info("Updating product with ID: {}", id);
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(ApiResponse.success(
                    HttpStatus.OK.value(),
                    ApiMessages.SUCCESS,
                    updatedProduct)); // Return HTTP 200 OK with updated product
        } catch (EntityNotFoundException e) {
            log.error("Product not found for ID: {}", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(
                            HttpStatus.NOT_FOUND.value(),
                            ApiMessages.ITEM_NOT_FOUND,
                            e.getMessage())); // Returns HTTP 404 Not Found if product doesn't exist
        } catch (Exception e) {
            log.error("Error updating product with ID: {}", id, e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            ApiMessages.INTERNAL_ERROR,
                            e.getMessage())); // Returns HTTP 500 if an error occurs
        }
    }

    /**
     * Delete product by id.
     *
     * @param productId the product id
     */
    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(ApiResponse.success(
                    HttpStatus.NO_CONTENT.value(),
                    ApiMessages.SUCCESS,
                    null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(
                            HttpStatus.NOT_FOUND.value(),
                            ApiMessages.ITEM_NOT_FOUND,
                            e.getMessage())); // Returns HTTP 404 Not Found if product doesn't exist
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            ApiMessages.INTERNAL_ERROR,
                            e.getMessage())); // Returns HTTP 500 if an error occurs
        }
    }
}

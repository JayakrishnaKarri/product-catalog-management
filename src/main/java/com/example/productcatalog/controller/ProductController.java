package com.example.productcatalog.controller;

import com.example.productcatalog.dto.CreateProductRequest;
import com.example.productcatalog.dto.ProductDTO;
import com.example.productcatalog.dto.UpdateProductRequest;
import com.example.productcatalog.model.ProductCategory;
import com.example.productcatalog.model.ProductStatus;
import com.example.productcatalog.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST Controller for Product Catalog operations.
 * Provides CRUD operations and search functionality for products.
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Product Catalog", description = "Product management operations")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Create a new product
     */
    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product in the catalog")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Product name already exists")
    })
    public ResponseEntity<ProductDTO> createProduct(
            @Valid @RequestBody CreateProductRequest request) {
        ProductDTO createdProduct = productService.createProduct(request);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Get all products
     */
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves all products from the catalog")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @Parameter(description = "Sort by field (name, price, created)")
            @RequestParam(required = false) String sortBy) {
        
        List<ProductDTO> products;
        if ("name".equalsIgnoreCase(sortBy)) {
            products = productService.getProductsSortedByName();
        } else if ("price".equalsIgnoreCase(sortBy)) {
            products = productService.getProductsSortedByPrice();
        } else if ("created".equalsIgnoreCase(sortBy)) {
            products = productService.getProductsSortedByCreatedDate();
        } else {
            products = productService.getAllProducts();
        }
        
        return ResponseEntity.ok(products);
    }

    /**
     * Get product by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a specific product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Update an existing product
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "409", description = "Product name already exists")
    })
    public ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request) {
        ProductDTO updatedProduct = productService.updateProduct(id, request);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Delete a product
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Deletes a product from the catalog")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search products with multiple criteria
     */
    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Search products by various criteria")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    public ResponseEntity<List<ProductDTO>> searchProducts(
            @Parameter(description = "Product name (partial match)")
            @RequestParam(required = false) String name,
            
            @Parameter(description = "Product category")
            @RequestParam(required = false) ProductCategory category,
            
            @Parameter(description = "Product status")
            @RequestParam(required = false) ProductStatus status,
            
            @Parameter(description = "Minimum price")
            @RequestParam(required = false) BigDecimal minPrice,
            
            @Parameter(description = "Maximum price")
            @RequestParam(required = false) BigDecimal maxPrice) {
        
        List<ProductDTO> products = productService.searchProducts(
            name, category, status, minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    /**
     * Get products by category
     */
    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category", description = "Retrieves all products in a specific category")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(
            @Parameter(description = "Product category") @PathVariable ProductCategory category) {
        List<ProductDTO> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    /**
     * Get products by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get products by status", description = "Retrieves all products with a specific status")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    public ResponseEntity<List<ProductDTO>> getProductsByStatus(
            @Parameter(description = "Product status") @PathVariable ProductStatus status) {
        List<ProductDTO> products = productService.getProductsByStatus(status);
        return ResponseEntity.ok(products);
    }

    /**
     * Update product stock
     */
    @PatchMapping("/{id}/stock")
    @Operation(summary = "Update product stock", description = "Updates the stock quantity of a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid stock quantity"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> updateProductStock(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Parameter(description = "New stock quantity") @RequestParam Integer stock) {
        
        if (stock < 0) {
            return ResponseEntity.badRequest().build();
        }
        
        ProductDTO updatedProduct = productService.updateProductStock(id, stock);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Activate product
     */
    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate product", description = "Sets product status to ACTIVE")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product activated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> activateProduct(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        ProductDTO updatedProduct = productService.activateProduct(id);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Deactivate product
     */
    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate product", description = "Sets product status to INACTIVE")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product deactivated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> deactivateProduct(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        ProductDTO updatedProduct = productService.deactivateProduct(id);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Discontinue product
     */
    @PatchMapping("/{id}/discontinue")
    @Operation(summary = "Discontinue product", description = "Sets product status to DISCONTINUED")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product discontinued successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> discontinueProduct(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        ProductDTO updatedProduct = productService.discontinueProduct(id);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Get product count
     */
    @GetMapping("/count")
    @Operation(summary = "Get product count", description = "Returns the total number of products")
    @ApiResponse(responseCode = "200", description = "Count retrieved successfully")
    public ResponseEntity<Long> getProductCount() {
        long count = productService.getTotalProductCount();
        return ResponseEntity.ok(count);
    }

    /**
     * Check if product exists
     */
    @GetMapping("/{id}/exists")
    @Operation(summary = "Check product existence", description = "Checks if a product exists by ID")
    @ApiResponse(responseCode = "200", description = "Check completed successfully")
    public ResponseEntity<Boolean> productExists(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        boolean exists = productService.productExists(id);
        return ResponseEntity.ok(exists);
    }
}
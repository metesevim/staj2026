package com.metesevim.staj2026.controller;

import com.metesevim.staj2026.dto.ProductRequest;
import com.metesevim.staj2026.dto.ProductResponse;
import com.metesevim.staj2026.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.math.BigDecimal;
import java.util.List;

@Tag(
        name = "Product API",
        description = "REST endpoints for managing products"
)
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @Operation(
            summary = "Create product",
            description = "Creates a new product. ADMIN role is required."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductResponse createProduct(
            @Valid @RequestBody ProductRequest request
    ) {
        return productService.createProduct(request);
    }


    @Operation(
            summary = "Get all products",
            description = "Returns a list of all products."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }


    @Operation(
            summary = "Get product by id",
            description = "Returns a product using its id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }


    @Operation(
            summary = "Find product by name",
            description = "Returns a product with the given name."
    )
    @GetMapping("/search")
    public ProductResponse getProductByName(
            @RequestParam String name
    ) {
        return productService.getProductByName(name);
    }


    @Operation(
            summary = "Get active products",
            description = "Returns all active products."
    )
    @GetMapping("/active")
    public List<ProductResponse> getActiveProducts() {
        return productService.getActiveProducts();
    }


    @Operation(
            summary = "Filter products by minimum price",
            description = "Returns products whose price is greater than or equal to the given value."
    )
    @GetMapping("/minimum-price")
    public List<ProductResponse> getProductsByMinimumPrice(
            @RequestParam BigDecimal value
    ) {
        return productService.getProductsByMinimumPrice(value);
    }


    @Operation(
            summary = "Update product",
            description = "Updates an existing product. ADMIN role is required."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }


    @Operation(
            summary = "Delete product",
            description = "Deletes a product by id. ADMIN role is required."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
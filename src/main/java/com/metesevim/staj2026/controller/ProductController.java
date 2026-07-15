package com.metesevim.staj2026.controller;

import com.metesevim.staj2026.dto.ProductRequest;
import com.metesevim.staj2026.dto.ProductResponse;
import com.metesevim.staj2026.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductResponse createProduct(
            @Valid @RequestBody ProductRequest request
    ) {
        return productService.createProduct(request);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    public ProductResponse getProductByName(
            @RequestParam String name
    ) {
        return productService.getProductByName(name);
    }

    @GetMapping("/active")
    public List<ProductResponse> getActiveProducts() {
        return productService.getActiveProducts();
    }

    @GetMapping("/minimum-price")
    public List<ProductResponse> getProductsByMinimumPrice(
            @RequestParam BigDecimal value
    ) {
        return productService.getProductsByMinimumPrice(value);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request
    ) {
        return productService.updateProduct(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
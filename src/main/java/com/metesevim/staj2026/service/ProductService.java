package com.metesevim.staj2026.service;

import com.metesevim.staj2026.entity.Product;
import com.metesevim.staj2026.exception.ProductNotFoundException;
import com.metesevim.staj2026.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //CREATE PRODUCT
    public Product createProduct(Product product) {
        validateProduct(product);

        return productRepository.save(product);
    }

    //GET ALL PRODUCTS
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //GET A PRODUCT BY ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    //VALIDATION
    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException(
                    "Product name cannot be empty"
            );
        }

        if (product.getPrice() == null ||
                product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Product price must be greater than zero"
            );
        }

        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException(
                    "Product stock cannot be negative"
            );
        }
    }

    //UPDATE PRODUCT
    public Product updateProduct(Long id, Product updatedProduct) {

        Product existingProduct = getProductById(id);

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setActive(updatedProduct.getActive());

        validateProduct(existingProduct);

        return productRepository.save(existingProduct);
    }
}
package com.metesevim.staj2026.service;

import com.metesevim.staj2026.dto.ProductRequest;
import com.metesevim.staj2026.dto.ProductResponse;
import com.metesevim.staj2026.entity.AppUser;
import com.metesevim.staj2026.entity.Product;
import com.metesevim.staj2026.exception.ProductNotFoundException;
import com.metesevim.staj2026.exception.ProductVersionConflictException;
import com.metesevim.staj2026.repository.ProductRepository;
import com.metesevim.staj2026.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    //CREATE PRODUCT
    public ProductResponse createProduct(ProductRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        AppUser seller = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Authenticated user not found")
                );

        Product product = new Product();

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setActive(
                request.active() != null ? request.active() : true
        );
        product.setSeller(seller);



        Product savedProduct = productRepository.save(product);

        return toResponse(savedProduct);
    }

    //GET ALL PRODUCTS
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //GET PRODUCT BY ID
    public ProductResponse getProductById(Long id) {
        Product product = findProductById(id);

        return toResponse(product);
    }

    //GET PRODUCT BY NAME
    public ProductResponse getProductByName(String name) {
        Product product = productRepository.findByNameIgnoreCase(name)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Product not found with name: " + name
                        )
                );

        return toResponse(product);
    }

    //GET ACTIVE PRODUCTS
    public List<ProductResponse> getActiveProducts() {
        return productRepository.findAllActiveProducts()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //GET PRODUCTS BY MINIMUM PRICE
    public List<ProductResponse> getProductsByMinimumPrice(
            BigDecimal minimumPrice
    ) {
        return productRepository
                .findProductsByMinimumPrice(minimumPrice)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // UPDATE PRODUCT
    public ProductResponse updateProduct(
            Long id,
            ProductRequest request
    ) {
        Product existingProduct = findProductById(id);

        if (request.version() == null) {
            throw new IllegalArgumentException(
                    "Product version is required for update"
            );
        }

        if (!existingProduct.getVersion().equals(request.version())) {
            throw new ProductVersionConflictException(
                    "Product has already been updated by another request"
            );
        }

        existingProduct.setName(request.name());
        existingProduct.setDescription(request.description());
        existingProduct.setPrice(request.price());
        existingProduct.setStock(request.stock());
        existingProduct.setActive(request.active());

        Product updatedProduct = productRepository.save(existingProduct);

        return toResponse(updatedProduct);
    }

    //DELETE PRODUCT
    public void deleteProduct(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    //FIND PRODUCT BY ID (FOR UPDATE & DELETE)
    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getActive(),
                product.getVersion(),
                product.getSeller() == null
                        ? null
                        : product.getSeller().getUsername()
        );
    }


}
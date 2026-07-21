package com.metesevim.staj2026.service;

import com.metesevim.staj2026.dto.ProductRequest;
import com.metesevim.staj2026.dto.ProductResponse;
import com.metesevim.staj2026.entity.Product;
import com.metesevim.staj2026.exception.ProductNotFoundException;
import com.metesevim.staj2026.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProductSuccessfully() {
        ProductRequest request = new ProductRequest(
                "Gaming Mouse",
                "Wireless gaming mouse",
                new BigDecimal("1499.90"),
                10,
                true,
                1L
        );

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Gaming Mouse");
        savedProduct.setDescription("Wireless gaming mouse");
        savedProduct.setPrice(new BigDecimal("1499.90"));
        savedProduct.setStock(10);
        savedProduct.setActive(true);

        when(productRepository.save(any(Product.class)))
                .thenReturn(savedProduct);

        ProductResponse response = productService.createProduct(request);

        assertEquals(1L, response.id());
        assertEquals("Gaming Mouse", response.name());
        assertEquals(new BigDecimal("1499.90"), response.price());
        assertEquals(10, response.stock());
        assertEquals(true, response.active());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldSetActiveTrueWhenActiveIsNull() {
        ProductRequest request = new ProductRequest(
                "Mechanical Keyboard",
                "RGB mechanical keyboard",
                new BigDecimal("2499.90"),
                12,
                null,
                1L
        );

        Product savedProduct = new Product();
        savedProduct.setId(2L);
        savedProduct.setName("Mechanical Keyboard");
        savedProduct.setDescription("RGB mechanical keyboard");
        savedProduct.setPrice(new BigDecimal("2499.90"));
        savedProduct.setStock(12);
        savedProduct.setActive(true);

        when(productRepository.save(any(Product.class)))
                .thenReturn(savedProduct);

        ProductResponse response = productService.createProduct(request);

        ArgumentCaptor<Product> productCaptor =
                ArgumentCaptor.forClass(Product.class);

        verify(productRepository).save(productCaptor.capture());

        Product productSentToRepository = productCaptor.getValue();

        assertEquals(true, productSentToRepository.getActive());
        assertEquals(true, response.active());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        Long productId = 999L;

        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(productId)
        );

        assertEquals(
                "Product not found with id: 999",
                exception.getMessage()
        );

        verify(productRepository).findById(productId);
    }

    @Test
    void shouldReturnProductWhenProductExists() {
        Long productId = 1L;

        Product product = new Product();
        product.setId(productId);
        product.setName("Gaming Mouse");
        product.setDescription("Wireless mouse");
        product.setPrice(new BigDecimal("1499.90"));
        product.setStock(10);
        product.setActive(true);

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        ProductResponse response =
                productService.getProductById(productId);

        assertEquals(productId, response.id());
        assertEquals("Gaming Mouse", response.name());
        assertEquals(new BigDecimal("1499.90"), response.price());
        assertEquals(10, response.stock());
        assertEquals(true, response.active());

        verify(productRepository).findById(productId);
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        Long productId = 1L;

        Product product = new Product();
        product.setId(productId);
        product.setName("Gaming Mouse");

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        productService.deleteProduct(productId);

        verify(productRepository).findById(productId);
        verify(productRepository).delete(product);
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        Long productId = 1L;

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Gaming Mouse");
        existingProduct.setDescription("Old description");
        existingProduct.setPrice(new BigDecimal("1499.90"));
        existingProduct.setStock(10);
        existingProduct.setActive(true);

        ProductRequest request = new ProductRequest(
                "Gaming Mouse Pro",
                "Updated description",
                new BigDecimal("1799.90"),
                20,
                false,
                1L
        );

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(existingProduct));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse response =
                productService.updateProduct(productId, request);

        assertEquals(productId, response.id());
        assertEquals("Gaming Mouse Pro", response.name());
        assertEquals("Updated description", response.description());
        assertEquals(new BigDecimal("1799.90"), response.price());
        assertEquals(20, response.stock());
        assertEquals(false, response.active());

        verify(productRepository).findById(productId);
        verify(productRepository).save(existingProduct);
    }
}
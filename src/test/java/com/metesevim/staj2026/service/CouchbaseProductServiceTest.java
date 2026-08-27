package com.metesevim.staj2026.service;

import com.metesevim.staj2026.document.CouchbaseProductDocument;
import com.metesevim.staj2026.repository.CouchbaseProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouchbaseProductServiceTest {

    @Mock
    private CouchbaseProductRepository repository;

    @InjectMocks
    private CouchbaseProductService service;

    @Test
    void shouldCreateProduct() {
        CouchbaseProductDocument product = product("product::1");
        when(repository.save(product)).thenReturn(product);

        assertEquals(product, service.create(product));
    }

    @Test
    void shouldReturnAllProducts() {
        CouchbaseProductDocument product = product("product::1");
        when(repository.findAll()).thenReturn(List.of(product));

        assertEquals(List.of(product), service.findAll());
    }

    @Test
    void shouldReturnProductById() {
        CouchbaseProductDocument product = product("product::1");
        when(repository.findById("product::1")).thenReturn(Optional.of(product));

        assertEquals(product, service.findById("product::1"));
    }

    @Test
    void shouldReturnNotFoundForMissingProduct() {
        when(repository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> service.findById("missing"));
    }

    @Test
    void shouldUpdateProduct() {
        CouchbaseProductDocument product = product(null);
        when(repository.existsById("product::1")).thenReturn(true);
        when(repository.save(product)).thenReturn(product);

        CouchbaseProductDocument updated = service.update("product::1", product);

        assertEquals("product::1", updated.getId());
    }

    @Test
    void shouldDeleteProduct() {
        when(repository.existsById("product::1")).thenReturn(true);

        service.delete("product::1");

        verify(repository).deleteById("product::1");
    }

    private CouchbaseProductDocument product(String id) {
        return new CouchbaseProductDocument(
                id,
                "Test Product",
                new BigDecimal("149.99"),
                10,
                true
        );
    }
}

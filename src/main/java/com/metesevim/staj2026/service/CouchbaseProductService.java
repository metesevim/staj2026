package com.metesevim.staj2026.service;

import com.metesevim.staj2026.document.CouchbaseProductDocument;
import com.metesevim.staj2026.repository.CouchbaseProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CouchbaseProductService {

    private final CouchbaseProductRepository repository;

    public CouchbaseProductService(CouchbaseProductRepository repository) {
        this.repository = repository;
    }

    public CouchbaseProductDocument create(CouchbaseProductDocument product) {
        return repository.save(product);
    }

    public List<CouchbaseProductDocument> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .toList();
    }

    public CouchbaseProductDocument findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> notFound(id));
    }

    public CouchbaseProductDocument update(
            String id,
            CouchbaseProductDocument product
    ) {
        if (!repository.existsById(id)) {
            throw notFound(id);
        }

        product.setId(id);
        return repository.save(product);
    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw notFound(id);
        }

        repository.deleteById(id);
    }

    private ResponseStatusException notFound(String id) {
        return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Couchbase product not found: " + id
        );
    }
}

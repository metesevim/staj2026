package com.metesevim.staj2026.controller;

import com.metesevim.staj2026.document.CouchbaseProductDocument;
import com.metesevim.staj2026.service.CouchbaseProductService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/couchbase/products")
public class CouchbaseProductController {

    private final CouchbaseProductService service;

    public CouchbaseProductController(CouchbaseProductService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public CouchbaseProductDocument create(
            @RequestBody CouchbaseProductDocument product
    ) {
        return service.create(product);
    }

    @GetMapping
    public List<CouchbaseProductDocument> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CouchbaseProductDocument findById(@PathVariable String id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CouchbaseProductDocument update(
            @PathVariable String id,
            @RequestBody CouchbaseProductDocument product
    ) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}

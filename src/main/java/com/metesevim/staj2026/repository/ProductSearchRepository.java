package com.metesevim.staj2026.repository;

import com.metesevim.staj2026.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductSearchRepository
        extends ElasticsearchRepository<ProductDocument, Long> {

    List<ProductDocument> findByNameContainingIgnoreCase(String name);
}
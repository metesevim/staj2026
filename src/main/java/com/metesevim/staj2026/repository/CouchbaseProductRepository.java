package com.metesevim.staj2026.repository;

import com.metesevim.staj2026.document.CouchbaseProductDocument;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

public interface CouchbaseProductRepository
        extends CouchbaseRepository<CouchbaseProductDocument, String> {
}

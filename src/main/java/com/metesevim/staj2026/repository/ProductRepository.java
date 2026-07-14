package com.metesevim.staj2026.repository;

import com.metesevim.staj2026.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

}
package com.metesevim.staj2026.repository;

import com.metesevim.staj2026.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Derived query (only name)
    Optional<Product> findByNameIgnoreCase(String name);

    // JPQL query (entity and java fields)
    @Query("""
            SELECT p
            FROM Product p
            WHERE p.active = true
            ORDER BY p.name ASC
            """)
    List<Product> findAllActiveProducts();

    // Native SQL query (regular postgresql table)
    @Query(
            value = """
                    SELECT *
                    FROM products
                    WHERE price >= :minimumPrice
                    ORDER BY price ASC
                    """,
            nativeQuery = true
    )
    List<Product> findProductsByMinimumPrice(
            @Param("minimumPrice") BigDecimal minimumPrice
    );
}
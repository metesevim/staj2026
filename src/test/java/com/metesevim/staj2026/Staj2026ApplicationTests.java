package com.metesevim.staj2026;

import com.metesevim.staj2026.repository.ProductSearchRepository;
import com.metesevim.staj2026.repository.CouchbaseProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class Staj2026ApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockitoBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockitoBean
    private ProductSearchRepository productSearchRepository;

    @MockitoBean
    private CouchbaseProductRepository couchbaseProductRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void liquibaseMigrationsAreApplied() {
        Integer changeCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM DATABASECHANGELOG",
                Integer.class
        );

        assertEquals(2, changeCount);
    }
}

package com.metesevim.staj2026;

import com.metesevim.staj2026.repository.ProductSearchRepository;
import com.metesevim.staj2026.repository.CouchbaseProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class Staj2026ApplicationTests {

    @MockitoBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockitoBean
    private ProductSearchRepository productSearchRepository;

    @MockitoBean
    private CouchbaseProductRepository couchbaseProductRepository;

    @Test
    void contextLoads() {
    }
}

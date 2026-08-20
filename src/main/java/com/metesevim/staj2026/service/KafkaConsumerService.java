package com.metesevim.staj2026.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(
            topics = "product-events",
            groupId = "staj2026-group"
    )
    public void consume(String message) {
        System.out.println("Kafka message received: " + message);
    }
}
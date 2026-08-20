package com.metesevim.staj2026.controller;

import com.metesevim.staj2026.service.KafkaProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    public KafkaController(
            KafkaProducerService kafkaProducerService
    ) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/send")
    public String sendMessage(
            @RequestParam String message
    ) {
        kafkaProducerService.sendMessage(message);

        return "Message sent to Kafka: " + message;
    }
}
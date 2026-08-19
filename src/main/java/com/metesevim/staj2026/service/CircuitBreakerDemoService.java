package com.metesevim.staj2026.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CircuitBreakerDemoService {

    private final RestClient restClient = RestClient.create();

    @CircuitBreaker(
            name = "demoService",
            fallbackMethod = "fallback"
    )
    public String callExternalService() {
        return restClient.get()
                .uri("http://localhost:9999/unavailable-service")
                .retrieve()
                .body(String.class);
    }

    public String fallback(Throwable throwable) {
        return "External service is unavailable. Fallback response returned.";
    }
}
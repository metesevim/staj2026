package com.metesevim.staj2026.controller;

import com.metesevim.staj2026.service.CircuitBreakerDemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/circuit-breaker")
public class CircuitBreakerDemoController {

    private final CircuitBreakerDemoService circuitBreakerDemoService;

    public CircuitBreakerDemoController(
            CircuitBreakerDemoService circuitBreakerDemoService
    ) {
        this.circuitBreakerDemoService = circuitBreakerDemoService;
    }

    @GetMapping("/test")
    public String testCircuitBreaker() {
        return circuitBreakerDemoService.callExternalService();
    }
}
package com.metesevim.staj2026.controller;

import com.metesevim.staj2026.service.RateLimitDemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rate-limit")
public class RateLimitDemoController {

    private final RateLimitDemoService rateLimitDemoService;

    public RateLimitDemoController(RateLimitDemoService rateLimitDemoService) {
        this.rateLimitDemoService = rateLimitDemoService;
    }

    @GetMapping("/test")
    public String testRateLimit() {
        return rateLimitDemoService.limitedRequest();
    }
}
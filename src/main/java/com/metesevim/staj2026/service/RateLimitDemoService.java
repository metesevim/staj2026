package com.metesevim.staj2026.service;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class RateLimitDemoService {

    @RateLimiter(
            name = "demoRateLimiter",
            fallbackMethod = "rateLimitFallback"
    )
    public String limitedRequest() {
        return "Request successful";
    }

    public String rateLimitFallback(Throwable throwable) {
        return "Rate limit exceeded";
    }
}
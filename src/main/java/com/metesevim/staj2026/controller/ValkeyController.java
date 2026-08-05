package com.metesevim.staj2026.controller;

import com.metesevim.staj2026.service.ValkeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/valkey")
public class ValkeyController {

    private final ValkeyService valkeyService;

    public ValkeyController(ValkeyService valkeyService) {
        this.valkeyService = valkeyService;
    }

    @PostMapping
    public ResponseEntity<Void> save(
            @RequestParam String key,
            @RequestParam String value
    ) {
        valkeyService.save(key, value);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{key}")
    public ResponseEntity<String> get(@PathVariable String key) {
        String value = valkeyService.get(key);

        if (value == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(value);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> delete(@PathVariable String key) {
        valkeyService.delete(key);
        return ResponseEntity.noContent().build();
    }
}
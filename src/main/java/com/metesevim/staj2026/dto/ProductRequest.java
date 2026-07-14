package com.metesevim.staj2026.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "Product name cannot be empty")
        String name,

        String description,

        @NotNull(message = "Product price is required")
        @Positive(message = "Product price must be greater than zero")
        BigDecimal price,

        @NotNull(message = "Product stock is required")
        @PositiveOrZero(message = "Product stock cannot be negative")
        Integer stock,

        Boolean active
) {
}
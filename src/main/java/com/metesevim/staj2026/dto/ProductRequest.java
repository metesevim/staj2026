package com.metesevim.staj2026.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequest(

        @Schema(
                description = "Name of the product",
                example = "Gaming Mouse",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Product name cannot be empty")
        String name,

        @Schema(
                description = "Detailed description of the product",
                example = "Wireless RGB gaming mouse"
        )
        String description,

        @Schema(
                description = "Product sale price",
                example = "1499.99",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Product price is required")
        @Positive(message = "Product price must be greater than zero")
        BigDecimal price,

        @Schema(
                description = "Available stock quantity",
                example = "50",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Product stock is required")
        @PositiveOrZero(message = "Product stock cannot be negative")
        Integer stock,

        @Schema(
                description = "Whether the product is active",
                example = "true"
        )
        Boolean active,

        @Schema(
                description = "Product version",
                example = "1"
        )
        Long version
) {
}
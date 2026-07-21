package com.metesevim.staj2026.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Product response object")
public record ProductResponse(

        @Schema(description = "Unique product id", example = "1")
        Long id,

        @Schema(description = "Product name", example = "Gaming Mouse")
        String name,

        @Schema(description = "Product description", example = "Wireless RGB gaming mouse")
        String description,

        @Schema(description = "Product sale price", example = "1499.99")
        BigDecimal price,

        @Schema(description = "Available stock quantity", example = "50")
        Integer stock,

        @Schema(description = "Product status", example = "true")
        Boolean active,

        @Schema(description = "Product version", example = "1")
        Long version


) {
}
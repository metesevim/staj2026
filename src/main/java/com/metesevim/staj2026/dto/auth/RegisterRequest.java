package com.metesevim.staj2026.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @Schema(
                description = "Unique username",
                example = "mete"
        )
        @NotBlank(message = "Username cannot be empty")
        @Size(min = 3, max = 30)
        String username,

        @Schema(
                description = "Password with at least 6 characters",
                example = "123456"
        )
        @NotBlank(message = "Password cannot be empty")
        @Size(min = 6)
        String password
) {
}
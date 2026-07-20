package com.metesevim.staj2026.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @Schema(
                description = "Registered username",
                example = "mete"
        )
        @NotBlank(message = "Username cannot be empty")
        String username,

        @Schema(
                description = "User password",
                example = "123456"
        )
        @NotBlank(message = "Password cannot be empty")
        String password
) {
}
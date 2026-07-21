package com.metesevim.staj2026.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
                message = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character."
        )
        String password
) {
}
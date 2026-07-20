package com.metesevim.staj2026.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response containing a JWT")
public record AuthResponse(

        @Schema(
                description = "JWT access token",
                example = "eyJhbGciOiJIUzI1NiJ9..."
        )
        String token,

        @Schema(description = "Authenticated username", example = "mete")
        String username,

        @Schema(description = "User role", example = "USER")
        String role
) {
}
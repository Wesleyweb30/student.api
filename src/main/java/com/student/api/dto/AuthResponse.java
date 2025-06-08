package com.student.api.dto;

public record AuthResponse(
        String token,
        String tipo,
        String email) {
}

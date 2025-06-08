package com.student.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InstituicaoRegisterDTO(
                @NotBlank String nome,
                @Email String email,
                @NotBlank String senha) {
}

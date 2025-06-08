package com.student.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfessorRegisterDTO(
    @NotBlank String nome,
    @NotBlank @Email String email,
    @NotBlank String senha,
    @NotNull Long instituicaoId
) {}

package com.student.api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record EventoCreateDTO(
    String nome,
    @NotBlank LocalDate data,
    @NotBlank Long professorId
) {}

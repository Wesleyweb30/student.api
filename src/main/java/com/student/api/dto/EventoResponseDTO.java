package com.student.api.dto;

import java.time.LocalDate;

public record EventoResponseDTO(
    Long id,
    String nome,
    LocalDate data,
    String escola
) {}

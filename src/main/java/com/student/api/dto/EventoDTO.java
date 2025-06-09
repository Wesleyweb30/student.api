package com.student.api.dto;

import java.time.LocalDate;

import com.student.api.model.Evento;

// EventoDTO.java
public record EventoDTO(
    Long id,
    String nome,
    LocalDate data
) {
    public EventoDTO(Evento evento) {
        this(evento.getId(), evento.getNome(), evento.getData());
    }
}

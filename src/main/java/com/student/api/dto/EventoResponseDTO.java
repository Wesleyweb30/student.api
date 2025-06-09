package com.student.api.dto;

import java.time.LocalDate;

import com.student.api.model.Evento;

public record EventoResponseDTO(
        Long id,
        String nome,
        LocalDate data,
        String nomeInstituicao
) {
    public EventoResponseDTO(Evento evento) {
        this(
            evento.getId(),
            evento.getNome(),
            evento.getData(),
            evento.getInstituicaoCriadora().getNome()
        );
    }
}
package com.student.api.dto;

import com.student.api.model.Professor;

import lombok.Data;

@Data
public class ProfessorListResponseDTO {
        private Long id;
    private String nome;

    public ProfessorListResponseDTO(Professor professor) {
        this.id = professor.getId();
        this.nome = professor.getNome();
    }
}

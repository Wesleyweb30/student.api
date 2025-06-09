package com.student.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.api.model.Instituicao;
import com.student.api.model.Professor;


public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByEmail(String email);
    List<Professor> findByInstituicao(Instituicao instituicao);

}

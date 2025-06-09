package com.student.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.api.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByInstituicaoCriadoraId(Long instituicaoId);
    List<Evento> findByInstituicaoCriadoraEmail(String email);
    List<Evento> findByProfessorResponsavelEmail(String email);
}

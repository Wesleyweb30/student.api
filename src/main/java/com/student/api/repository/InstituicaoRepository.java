package com.student.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.api.model.Instituicao;

public interface InstituicaoRepository  extends JpaRepository<Instituicao, Long>{
     Optional<Instituicao> findByEmail(String email);
}

package com.student.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.api.dto.ProfessorListResponseDTO;
import com.student.api.dto.ProfessorRegisterDTO;
import com.student.api.dto.ProfessorUpdateDTO;
import com.student.api.model.Instituicao;
import com.student.api.model.Professor;
import com.student.api.model.UserType;
import com.student.api.repository.InstituicaoRepository;
import com.student.api.repository.ProfessorRepository;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository profRepo;
    @Autowired
    private InstituicaoRepository instituicaoRepo;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public ProfessorRepository getRepository() {
        return profRepo;
    }

    public void cadastrar(ProfessorRegisterDTO dto) {
        if (profRepo.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado para professor.");
        }

        Instituicao instituicao = instituicaoRepo.findById(dto.instituicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Instituição não encontrada."));

        Professor professor = new Professor();
        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setSenha(encoder.encode(dto.senha()));
        professor.setTipo(UserType.PROFESSOR);
        professor.setInstituicao(instituicao);

        profRepo.save(professor);
    }

    public void atualizar(Long id, ProfessorUpdateDTO dto, String email) {
        Professor prof = profRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        if (!prof.getEmail().equals(email)) {
            throw new SecurityException("Você só pode editar seu próprio perfil.");
        }

        prof.setNome(dto.nome());
        prof.setSenha(encoder.encode(dto.senha()));
        profRepo.save(prof);
    }

    public List<ProfessorListResponseDTO> listarTodos() {
        return profRepo.findAll()
                .stream()
                .map(ProfessorListResponseDTO::new)
                .collect(Collectors.toList());
    }
}
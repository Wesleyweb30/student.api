package com.student.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.api.dto.InstituicaoRegisterDTO;
import com.student.api.model.Instituicao;
import com.student.api.model.UserType;
import com.student.api.repository.InstituicaoRepository;

@Service
public class InstituicaoService {
    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public InstituicaoRepository getRepository() {
        return instituicaoRepository;
    }

    public void cadastrar(InstituicaoRegisterDTO dto) {
        if (instituicaoRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Email já está em uso.");
        }

        Instituicao instituicao = new Instituicao();
        instituicao.setNome(dto.nome());
        instituicao.setEmail(dto.email());
        instituicao.setSenha(passwordEncoder.encode(dto.senha()));
        instituicao.setTipo(UserType.INSTITUICAO);

        instituicaoRepository.save(instituicao);
    }

    public List<Instituicao> listar() {
        return instituicaoRepository.findAll();
    }
}

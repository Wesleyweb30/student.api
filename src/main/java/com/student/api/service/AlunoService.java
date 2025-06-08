package com.student.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.api.dto.AlunoRegisterDTO;
import com.student.api.dto.AlunoUpdateDTO;
import com.student.api.model.Aluno;
import com.student.api.model.Instituicao;
import com.student.api.model.UserType;
import com.student.api.repository.AlunoRepository;
import com.student.api.repository.InstituicaoRepository;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepo;
    @Autowired
    private InstituicaoRepository instituicaoRepo;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public AlunoRepository getRepository() { return alunoRepo; }

    public void cadastrar(AlunoRegisterDTO dto) {
        if (alunoRepo.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado para aluno.");
        }
        
        Instituicao instituicao = instituicaoRepo.findById(dto.instituicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Instituição não encontrada."));
        System.out.println("Aluno: " + dto.nome());
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setSenha(encoder.encode(dto.senha()));
        aluno.setTipo(UserType.ALUNO);
        aluno.setInstituicao(instituicao);

        alunoRepo.save(aluno);
    }

    public void atualizar(Long id, AlunoUpdateDTO dto, String email) {
        Aluno aluno = alunoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        if (!aluno.getEmail().equals(email)) {
            throw new SecurityException("Você só pode editar seu próprio perfil.");
        }

        aluno.setNome(dto.nome());
        aluno.setSenha(encoder.encode(dto.senha()));
        alunoRepo.save(aluno);
    }
}

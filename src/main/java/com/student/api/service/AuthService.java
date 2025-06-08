package com.student.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.api.dto.AuthRequest;
import com.student.api.dto.AuthResponse;
import com.student.api.model.User;
import com.student.api.repository.AlunoRepository;
import com.student.api.repository.InstituicaoRepository;
import com.student.api.repository.ProfessorRepository;
import com.student.api.util.JwtUtil;

@Service
public class AuthService {
    @Autowired
    private InstituicaoRepository instituicaoRepo;
    @Autowired
    private ProfessorRepository profRepo;
    @Autowired
    private AlunoRepository alunoRepo;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse autenticar(AuthRequest req) {
        String email = req.email();
        String senha = req.senha();

        Optional<? extends User> usuarioOpt = instituicaoRepo.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = profRepo.findByEmail(email);
        }
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = alunoRepo.findByEmail(email);
        }
        User usuario = usuarioOpt.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        if (!encoder.matches(senha, usuario.getSenha())) {
            throw new IllegalArgumentException("Senha inválida.");
        }

        String token = jwtUtil.gerarToken(usuario.getEmail(), usuario.getTipo());

        return new AuthResponse(token, usuario.getTipo().name(), usuario.getEmail());
    }
}

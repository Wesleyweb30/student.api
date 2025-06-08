package com.student.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.api.dto.AlunoRegisterDTO;
import com.student.api.dto.AuthRequest;
import com.student.api.dto.AuthResponse;
import com.student.api.dto.InstituicaoRegisterDTO;
import com.student.api.dto.ProfessorRegisterDTO;
import com.student.api.service.AlunoService;
import com.student.api.service.AuthService;
import com.student.api.service.InstituicaoService;
import com.student.api.service.ProfessorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private InstituicaoService instituicaoService;
    @Autowired
    private AlunoService alunoService;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private AuthService authService;

    @PostMapping("/register/instituicao")
    public ResponseEntity<?> registrarEscola(@RequestBody @Valid InstituicaoRegisterDTO dto) {
        try {
            instituicaoService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Instituição cadastrada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register/aluno")
    public ResponseEntity<?> registrarAluno(@RequestBody @Valid AlunoRegisterDTO dto) {
        try {
            alunoService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Aluno cadastrado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register/professor")
    public ResponseEntity<?> registrarProfessor(@RequestBody @Valid ProfessorRegisterDTO dto) {
        try {
            professorService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Professor cadastrado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
         System.out.println("Login chamado para: " + request.email());
        try {
            AuthResponse response = authService.autenticar(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

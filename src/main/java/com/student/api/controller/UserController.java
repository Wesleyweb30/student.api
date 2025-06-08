package com.student.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.api.dto.AlunoUpdateDTO;
import com.student.api.dto.ProfessorUpdateDTO;
import com.student.api.service.AlunoService;
import com.student.api.service.InstituicaoService;
import com.student.api.service.ProfessorService;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private AlunoService alunoService;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private InstituicaoService instituicaoService;

    @PreAuthorize("hasRole('ALUNO')")
    @PutMapping("/aluno/{id}")
    public ResponseEntity<?> atualizarAluno(@PathVariable Long id, @RequestBody AlunoUpdateDTO dto,
            Authentication auth) {
        alunoService.atualizar(id, dto, auth.getName());
        return ResponseEntity.ok("Aluno atualizado com sucesso.");
    }

    @PreAuthorize("hasRole('PROFESSOR')")
    @PutMapping("/professor/{id}")
    public ResponseEntity<?> atualizarProfessor(@PathVariable Long id, @RequestBody ProfessorUpdateDTO dto,
            Authentication auth) {
        professorService.atualizar(id, dto, auth.getName());
        return ResponseEntity.ok("Professor atualizado com sucesso.");
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @GetMapping("/alunos")
    public ResponseEntity<?> listarAlunos(Authentication auth) {
        String emailInstituicao = auth.getName();
        // Pega todos os alunos da instituição logada
        var instituicao = instituicaoService.getRepository().findByEmail(emailInstituicao)
                .orElseThrow(() -> new IllegalArgumentException("Instituição não encontrada"));
        var alunos = alunoService.getRepository().findAll().stream()
                .filter(a -> a.getInstituicao().getId().equals(instituicao.getId()))
                .toList();
        return ResponseEntity.ok(alunos);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @GetMapping("/professores")
    public ResponseEntity<?> listarProfessores(Authentication auth) {
        String emailInstituicao = auth.getName();
        var instituicao = instituicaoService.getRepository().findByEmail(emailInstituicao)
                .orElseThrow(() -> new IllegalArgumentException("Instituição não encontrada"));
        var professores = professorService.getRepository().findAll().stream()
                .filter(p -> p.getInstituicao().getId().equals(instituicao.getId()))
                .toList();
        return ResponseEntity.ok(professores);
    }

    @GetMapping("/me")
    public ResponseEntity<?> perfilLogado(Authentication auth) {
        String email = auth.getName();

        var instituicao = instituicaoService.getRepository().findByEmail(email);
        if (instituicao.isPresent())
            return ResponseEntity.ok(instituicao.get());

        var professor = professorService.getRepository().findByEmail(email);
        if (professor.isPresent())
            return ResponseEntity.ok(professor.get());

        var aluno = alunoService.getRepository().findByEmail(email);
        if (aluno.isPresent())
            return ResponseEntity.ok(aluno.get());

        return ResponseEntity.status(404).body("Usuário não encontrado");
    }

}

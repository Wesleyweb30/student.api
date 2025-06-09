package com.student.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.api.dto.InstituicaoListResponse;
import com.student.api.dto.ProfessorListResponseDTO;
import com.student.api.model.Instituicao;
import com.student.api.service.InstituicaoService;

@RestController
@RequestMapping("/instituicoes")
public class InstituicaoController {

    @Autowired
    private InstituicaoService instituicaoService;

    @GetMapping
    public List<InstituicaoListResponse> listarTodas() {
        List<Instituicao> instituicoes = instituicaoService.listar();
        return instituicoes.stream()
                .map(i -> new InstituicaoListResponse(i.getId(), i.getNome()))
                .toList();
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @GetMapping("/professores")
    public ResponseEntity<?> listarProfessores(Authentication auth) {
        List<ProfessorListResponseDTO> professores = instituicaoService.listarProfessoresDaInstituicao(auth.getName());
        return ResponseEntity.ok(professores);
    }

}

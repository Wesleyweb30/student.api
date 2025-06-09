package com.student.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.api.dto.InstituicaoListResponse;
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
}

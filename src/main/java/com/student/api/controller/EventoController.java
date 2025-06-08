package com.student.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.api.dto.EventoCreateDTO;
import com.student.api.service.EventoService;

@RestController
@RequestMapping("/eventos")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @PreAuthorize("hasRole('INSTITUICAO')")
    @PostMapping
    public ResponseEntity<?> criarEvento(@RequestBody EventoCreateDTO dto, Authentication auth) {
        eventoService.criarEvento(dto, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Evento criado com sucesso: ");
    }

    @PreAuthorize("hasRole('ALUNO')")
    @PostMapping("/{id}/inscrever")
    public ResponseEntity<String> inscrever(@PathVariable Long id, Authentication auth) {
        eventoService.inscreverAluno(id, auth.getName());
        return ResponseEntity.ok("Inscrição realizada com sucesso.");
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(eventoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        var evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(evento);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id, Authentication auth) {
        eventoService.deletar(id, auth.getName());
        return ResponseEntity.ok("Evento excluído.");
    }
}

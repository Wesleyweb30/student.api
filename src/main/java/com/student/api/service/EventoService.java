package com.student.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.api.dto.EventoCreateDTO;
import com.student.api.model.Aluno;
import com.student.api.model.Evento;
import com.student.api.model.Instituicao;
import com.student.api.model.Professor;
import com.student.api.repository.AlunoRepository;
import com.student.api.repository.EventoRepository;
import com.student.api.repository.InstituicaoRepository;
import com.student.api.repository.ProfessorRepository;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepo;
    @Autowired
    private InstituicaoRepository instituicaoRepo;
    @Autowired
    private AlunoRepository alunoRepo;
    @Autowired
    private ProfessorRepository professorRepo;

    public Evento criarEvento(EventoCreateDTO dto, String emailInstituicao) {
        Instituicao instituicao = instituicaoRepo.findByEmail(emailInstituicao)
                .orElseThrow(() -> new IllegalArgumentException("Instituição não encontrada"));

        Professor professor = professorRepo.findById(dto.professorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        Evento evento = new Evento();
        evento.setNome(dto.nome());
        evento.setData(dto.data());
        evento.setInstituicaoCriadora(instituicao);
        evento.setProfessorResponsavel(professor);

        return eventoRepo.save(evento);
    }

    public List<Evento> listarTodos() {
        return eventoRepo.findAll();
    }

    public void inscreverAluno(Long eventoId, String emailAluno) {
        Evento evento = eventoRepo.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

        Aluno aluno = alunoRepo.findByEmail(emailAluno)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        if (!evento.getAlunosInscritos().contains(aluno)) {
            evento.getAlunosInscritos().add(aluno);
            eventoRepo.save(evento);
        }
    }

    public Evento buscarPorId(Long id) {
        return eventoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
    }

    public void deletar(Long id, String emailInstituicao) {
        Evento evento = eventoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

        if (!evento.getInstituicaoCriadora().getEmail().equals(emailInstituicao)) {
            throw new SecurityException("Apenas a instituição criadora pode deletar.");
        }

        eventoRepo.delete(evento);
    }
}

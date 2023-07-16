package com.escalacerta.autoescolabellas.controller;

import com.escalacerta.autoescolabellas.model.Aula;
import com.escalacerta.autoescolabellas.model.Aluno;
import com.escalacerta.autoescolabellas.model.Instrutor;
import com.escalacerta.autoescolabellas.repository.AulaRepository;
import com.escalacerta.autoescolabellas.repository.AlunoRepository;
import com.escalacerta.autoescolabellas.repository.InstrutorRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Controller
public class AulaController {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private InstrutorRepository instrutorRepository;

    @GetMapping("/home")
    public String home() {
        return "home";
    }
    @PostMapping("/home")
    public String loginAdmin() {
        return "redirect:/home";
    }

    @RequestMapping("/api")
    @ResponseBody
    public String welcome() {
        return "Welcome!";
    }

    @GetMapping("/api/events")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public Iterable<Aula> events(@RequestParam("start") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start, @RequestParam("end") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {
        return aulaRepository.findBetween(start, end);
    }

    @PostMapping("/api/events/create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    public Aula createAula(@RequestBody EventCreateParams params) {

        Aluno aluno = alunoRepository.findById(params.alunoId).orElse(null);
        Instrutor instrutor = instrutorRepository.findById(params.instrutorId).orElse(null);

        if (aluno != null && instrutor != null) {
            Aula aula = new Aula();
            aula.setStart(params.start);
            aula.setEnd(params.end);
            aula.setAluno(aluno);
            aula.setInstrutor(instrutor);

            aulaRepository.save(aula);

            return aula;
        }

        return null;
    }

    @PostMapping("/api/events/move")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    public Aula moveAula(@RequestBody EventMoveParams params) {

        Aula aula = aulaRepository.findById(params.id).orElse(null);

        if (aula != null) {
            aula.setStart(params.start);
            aula.setEnd(params.end);

            aulaRepository.save(aula);
        }

        return aula;
    }

    public static class EventCreateParams {
        public LocalDateTime start;
        public LocalDateTime end;
        public Long alunoId;
        public Long instrutorId;
    }

    public static class EventMoveParams {
        public Long id;
        public LocalDateTime start;
        public LocalDateTime end;
        public Long resource;
    }
}
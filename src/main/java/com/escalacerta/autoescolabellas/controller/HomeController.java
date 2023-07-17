package com.escalacerta.autoescolabellas.controller;

import com.escalacerta.autoescolabellas.dto.AgendamentoRequest;
import com.escalacerta.autoescolabellas.model.Aluno;
import com.escalacerta.autoescolabellas.model.Aula;
import com.escalacerta.autoescolabellas.model.Instrutor;
import com.escalacerta.autoescolabellas.repository.AgendamentoRepository;
import com.escalacerta.autoescolabellas.repository.AlunoRepository;
import com.escalacerta.autoescolabellas.repository.InstrutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {

    private final AgendamentoRepository agendamentoRepository;
    private final AlunoRepository alunoRepository;
    private final InstrutorRepository instrutorRepository;

    @Autowired
    public HomeController(AgendamentoRepository agendamentoRepository,
                          AlunoRepository alunoRepository,
                          InstrutorRepository instrutorRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.alunoRepository = alunoRepository;
        this.instrutorRepository = instrutorRepository;
    }

    @GetMapping("/home")
    public String home(Model model) {
        // Buscar dados relevantes para a página home
        List<Aluno> alunos = alunoRepository.findAll();
        List<Instrutor> instrutores = instrutorRepository.findAll();

        // Passar os dados para o modelo
        model.addAttribute("alunos", alunos);
        model.addAttribute("instrutores", instrutores);

        return "home";
    }

    @PostMapping("/home")
    public String loginAdmin() {
        return "redirect:/home";
    }

    @PostMapping("/api/agendamento")
    @ResponseBody
    public String salvarAgendamento(@RequestBody AgendamentoRequest agendamentoRequest) {
        // Recuperar os dados do request
        String horario = agendamentoRequest.getHorario();
        String dia = agendamentoRequest.getDia();
        Long alunoId = agendamentoRequest.getAlunoId();

        try {
            // Buscar o aluno pelo ID
            Aluno aluno = alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com o ID: " + alunoId));

            // Criar o objeto de agendamento
            Aula agendamento = new Aula();
            agendamento.setHorario(horario);
            agendamento.setDia(dia);
            agendamento.setAluno(aluno);

            // Salvar o agendamento no banco de dados
            agendamentoRepository.save(agendamento);

            return "Agendamento salvo com sucesso!";
        } catch (Exception e) {
            return "Erro ao salvar o agendamento: " + e.getMessage();
        }
    }
}

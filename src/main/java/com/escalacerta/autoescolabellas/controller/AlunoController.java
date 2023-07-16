package com.escalacerta.autoescolabellas.controller;

import java.util.List;


import com.escalacerta.autoescolabellas.service.AlunoService;
import com.escalacerta.autoescolabellas.model.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlunoController {

	@Autowired
	private AlunoService alunoService;


	// display list of employees
	@GetMapping("/viewHomePage")
	public String viewHomePage(Model model) {
		return findPaginated(1, "nome", "asc", model);
	}

	@GetMapping("/showNewAlunoForm")
	public String showNewAlunoForm(Model model) {
		// create model attribute to bind form data
		Aluno aluno = new Aluno();
		model.addAttribute("aluno", aluno);
		return "new_aluno";
	}

	@PostMapping("/saveAluno")
	public String saveAluno(@ModelAttribute("aluno") Aluno aluno) {
		// save employee to database
		alunoService.saveAluno(aluno);
		return "redirect:/viewHomePage";
	}

	@GetMapping("/aluno/showFormForUpdate/{id}")
	public String showFormForUpdateAluno(@PathVariable(value = "id") long id, Model model) {
		// get aluno from the service
		Aluno aluno = alunoService.getAlunoById(id);

		// set aluno as a model attribute to pre-populate the form
		model.addAttribute("aluno", aluno);
		return "update_aluno";
	}


	@GetMapping("/deleteAluno/{id}")
	public String deleteAluno(@PathVariable (value = "id") long id) {

		// call delete employee method
		this.alunoService.deleteAlunoById(id);
		return "redirect:/viewHomePage";
	}


	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
								@RequestParam("sortField") String sortField,
								@RequestParam("sortDir") String sortDir,
								Model model) {
		int pageSize = 5;

		Page<Aluno> page = alunoService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Aluno> listAlunos = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listAlunos", listAlunos);
		return "aluno";
	}
}
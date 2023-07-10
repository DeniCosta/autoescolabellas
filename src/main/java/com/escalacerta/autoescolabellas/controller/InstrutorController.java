package com.escalacerta.autoescolabellas.controller;

import java.util.List;

import com.escalacerta.autoescolabellas.service.InstrutorService;
import com.escalacerta.autoescolabellas.model.Instrutor;
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
public class InstrutorController {

    @Autowired
    private InstrutorService instrutorService;

    @GetMapping("/instrutor/listInstrutor")
    public String listInstrutor(Model model) {
        List<Instrutor> listInstrutores = instrutorService.getAllInstrutores();
        model.addAttribute("listInstrutores", listInstrutores);
        return "instrutor";
    }

    @GetMapping("/instrutor/showNewInstrutorForm")
    public String showNewInstrutorForm(Model model) {
        // create model attribute to bind form data
        Instrutor instrutor = new Instrutor();
        model.addAttribute("instrutor", instrutor);
        return "new_instrutor";
    }

    @PostMapping("/instrutor/saveInstrutor")
    public String saveInstrutor(@ModelAttribute("instrutor") Instrutor instrutor) {
        // save instrutor to database
        instrutorService.saveInstrutor(instrutor);
        return "redirect:/instrutor/listInstrutor";
    }

    @GetMapping("/instrutor/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        // get instrutor from the service
        Instrutor instrutor = instrutorService.getInstrutorById(id);

        // set instrutor as a model attribute to pre-populate the form
        model.addAttribute("instrutor", instrutor);
        return "update_instrutor";
    }

    @GetMapping("/instrutor/deleteInstrutor/{id}")
    public String deleteInstrutor(@PathVariable(value = "id") long id) {
        // call delete instrutor method
        this.instrutorService.deleteInstrutorById(id);
        return "redirect:/instrutor/listInstrutor";
    }

    @GetMapping("/instrutor/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Instrutor> page = instrutorService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Instrutor> listInstrutores = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listInstrutores", listInstrutores);
        return "instrutor";
    }
}

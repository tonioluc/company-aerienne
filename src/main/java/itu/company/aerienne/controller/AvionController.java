package itu.company.aerienne.controller;

import itu.company.aerienne.model.Avion;
import itu.company.aerienne.service.AvionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/avions")
public class AvionController {

    @Autowired
    private AvionService service;


    @GetMapping
    public String list(Model model) {
        model.addAttribute("avions", service.findAll());
        return "avions/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("avion", new Avion());
        return "avions/form";
    }

    @PostMapping
    public String create(@ModelAttribute Avion avion) {
        service.save(avion);
        return "redirect:/avions";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Optional<Avion> existing = service.findById(id);
        if (existing.isEmpty()) {
            return "redirect:/avions";
        }
        model.addAttribute("avion", existing.get());
        return "avions/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Avion avion) {
        avion.setId_avion(id);
        service.save(avion);
        return "redirect:/avions";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/avions";
    }
}

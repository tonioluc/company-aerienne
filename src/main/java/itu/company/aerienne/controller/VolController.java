package itu.company.aerienne.controller;

import itu.company.aerienne.service.VolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vols")
public class VolController {

    @Autowired
    private VolService volService;

    @GetMapping
    public String listVols(Model model) {
        model.addAttribute("vols", volService.getAllVolDetails());
        return "vols/list";
    }
}

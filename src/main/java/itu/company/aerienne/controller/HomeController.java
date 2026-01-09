package itu.company.aerienne.controller;

import itu.company.aerienne.dto.ChiffreAffaireDto;
import itu.company.aerienne.service.AchatPlacesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class HomeController {

    @Autowired
    private AchatPlacesService achatPlacesService;

    @GetMapping("/")
    public String home(@RequestParam(value = "date", required = false) 
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                       Model model) {
        
        if (date != null) {
            ChiffreAffaireDto chiffreAffaire = achatPlacesService.getChiffreAffairesByDate(date);
            model.addAttribute("chiffreAffaire", chiffreAffaire);
            model.addAttribute("dateRecherche", date);
        }
        
        return "index";
    }
}

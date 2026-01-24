package itu.company.aerienne.controller;

import itu.company.aerienne.dto.VolChiffreAffaireDto;
import itu.company.aerienne.service.VolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/chiffre-affaires")
public class ChiffreAffaireController {

    @Autowired
    private VolService volService;

    @GetMapping
    public String showChiffreAffaires(
            @RequestParam(value = "date", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        if (date != null) {
            List<VolChiffreAffaireDto> volsCA = volService.getChiffreAffaireDetailByDate(date);
            model.addAttribute("volsCA", volsCA);
            model.addAttribute("dateRecherche", date);

            // Calculer les totaux globaux
            int totalPlacesGlobal = 0;
            BigDecimal totalCAGlobal = BigDecimal.ZERO;
            for (VolChiffreAffaireDto vol : volsCA) {
                totalPlacesGlobal += vol.getTotalPlacesVendues();
                totalCAGlobal = totalCAGlobal.add(vol.getChiffreAffaires());
            }
            model.addAttribute("totalPlacesGlobal", totalPlacesGlobal);
            model.addAttribute("totalCAGlobal", totalCAGlobal);
        }

        return "chiffre-affaires/chiffre-affaires-vol-jour";
    }
}

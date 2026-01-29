package itu.company.aerienne.controller;

import itu.company.aerienne.dto.VenteProduitViewDto;
import itu.company.aerienne.model.ProduitExtra;
import itu.company.aerienne.model.VenteProduitExtra;
import itu.company.aerienne.model.Vol;
import itu.company.aerienne.service.ProduitExtraService;
import itu.company.aerienne.service.VenteProduitExtraService;
import itu.company.aerienne.service.VolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ventes-produits")
public class VenteProduitController {

    @Autowired
    private VenteProduitExtraService venteService;

    @Autowired
    private ProduitExtraService produitService;

    @Autowired
    private VolService volService;

    @GetMapping
    public String list(Model model) {
        List<VenteProduitExtra> ventes = venteService.findAll();
        List<VenteProduitViewDto> views = new ArrayList<>();

        for (VenteProduitExtra v : ventes) {
            Vol vol = volService.findById(v.getIdVol()).orElse(null);
            ProduitExtra p = produitService.findById(v.getIdProduitExtra()).orElse(null);
            String volDesc = "-";
            if (vol != null) {
                // build a friendly description similar to VolDetailsDto
                volDesc = (vol.getIdVol() != null ? "Vol #" + vol.getIdVol() + " - " : "") + (vol.getDateHeureDepart() != null ? vol.getDateHeureDepart().toString() : "");
            }
            String prodNom = p != null ? p.getNom() : "-";
            views.add(new VenteProduitViewDto(v.getIdVenteProduitExtra(), volDesc, prodNom, v.getQuantite(), v.getDateVente()));
        }

        model.addAttribute("ventes", views);
        return "ventes/ventes-produits-list";
    }

    @GetMapping("/ajouter")
    public String showForm(Model model) {
        model.addAttribute("vols", volService.getAllVolDetails());
        model.addAttribute("produitsExtras", produitService.findAll());
        return "ventes/ventes-produits-form";
    }

    @PostMapping("/ajouter")
    public String save(
            @RequestParam("idVol") Integer idVol,
            @RequestParam("idProduitExtra") Integer idProduitExtra,
            @RequestParam("quantite") Integer quantite,
            RedirectAttributes redirectAttributes) {

        try {
            venteService.enregistrerVente(idVol, idProduitExtra, quantite);
            redirectAttributes.addFlashAttribute("successMessage", "Vente enregistrée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/ventes-produits";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        venteService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Vente supprimée.");
        return "redirect:/ventes-produits";
    }
}

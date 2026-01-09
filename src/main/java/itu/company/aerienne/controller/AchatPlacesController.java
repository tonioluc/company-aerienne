package itu.company.aerienne.controller;

import itu.company.aerienne.dto.AchatPlacesFormDto;
import itu.company.aerienne.model.AchatPlaces;
import itu.company.aerienne.model.Vol;
import itu.company.aerienne.service.AchatPlacesService;
import itu.company.aerienne.service.AeroportService;
import itu.company.aerienne.service.VolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/achats")
public class AchatPlacesController {

    @Autowired
    private AeroportService aeroportService;

    @Autowired
    private VolService volService;

    @Autowired
    private AchatPlacesService achatPlacesService;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("aeroports", aeroportService.findAll());
        model.addAttribute("achat", new AchatPlacesFormDto());
        return "avions/achat-places";
    }

    @PostMapping
    public String showAvailableVol(@ModelAttribute("achat") AchatPlacesFormDto donneesEnvoye,
                                   Model model,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        try {
            // Vérifier que les aéroports sont différents
            donneesEnvoye.estAeroportDepartArriveDifferent();

            // Stocker les données du formulaire en session
            session.setAttribute("achatForm", donneesEnvoye);

            // Récupérer les vols disponibles
            Map<Vol, Integer> volsDisponibles = volService.getVolDisponible(
                    donneesEnvoye.getAeroportDepartId(),
                    donneesEnvoye.getAeroportArriveId(),
                    donneesEnvoye.getPlaces()
            );

            // Passer les données à la vue
            model.addAttribute("volsDisponibles", volsDisponibles);
            model.addAttribute("clientNom", donneesEnvoye.getClientNomComplet());
            model.addAttribute("placeDemandee", donneesEnvoye.getPlaces());

            return "avions/liste-vol-dispo";

        } catch (IllegalArgumentException e) {
            // Erreur de validation: retourner au formulaire avec message
            model.addAttribute("aeroports", aeroportService.findAll());
            model.addAttribute("achat", donneesEnvoye);
            model.addAttribute("error", e.getMessage());
            return "avions/achat-places";
        }
    }

    @GetMapping("/prendre/{idVol}")
    public String prendrePlace(@PathVariable("idVol") Integer idVol,
                               HttpSession session,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        // Récupérer les données du formulaire depuis la session
        AchatPlacesFormDto achatForm = (AchatPlacesFormDto) session.getAttribute("achatForm");

        if (achatForm == null) {
            redirectAttributes.addFlashAttribute("error", "Session expirée. Veuillez recommencer.");
            return "redirect:/achats";
        }

        // Créer et enregistrer l'achat
        AchatPlaces achat = new AchatPlaces();
        achat.setNomClient(achatForm.getClientNomComplet());
        achat.setNombrePlace(String.valueOf(achatForm.getPlaces()));
        achat.setIdVol(idVol);

        achatPlacesService.save(achat);

        // Nettoyer la session
        session.removeAttribute("achatForm");

        // Rediriger avec message de succès
        redirectAttributes.addFlashAttribute("success", 
            "Achat confirmé ! " + achatForm.getPlaces() + " place(s) réservée(s) pour " + achatForm.getClientNomComplet());
        
        return "redirect:/";
    }

    @GetMapping("/liste")
    public String listeAchats(Model model) {
        model.addAttribute("achats", achatPlacesService.findAll());
        return "avions/liste-achats";
    }

    @PostMapping("/liste/{id}/delete")
    public String deleteAchat(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        achatPlacesService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Achat supprimé avec succès.");
        return "redirect:/achats/liste";
    }
}

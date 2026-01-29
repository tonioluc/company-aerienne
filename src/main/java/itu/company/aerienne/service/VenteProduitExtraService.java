package itu.company.aerienne.service;

import itu.company.aerienne.model.VenteProduitExtra;
import itu.company.aerienne.model.Vol;
import itu.company.aerienne.repository.VenteProduitExtraRepository;
import itu.company.aerienne.repository.VolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VenteProduitExtraService {

    @Autowired
    private VenteProduitExtraRepository repository;

    @Autowired
    private VolRepository volRepository;


    public List<VenteProduitExtra> findAll() {
        return repository.findAll();
    }

    public Optional<VenteProduitExtra> findById(Integer id) {
        return repository.findById(id);
    }

    public List<VenteProduitExtra> findByIdVol(Integer idVol) {
        return repository.findByIdVol(idVol);
    }

    public VenteProduitExtra save(VenteProduitExtra vente) {
        return repository.save(vente);
    }

    /**
     * Enregistre une vente de produit extra.
     * La date de vente est automatiquement définie à partir de la date de départ du vol.
     */
    public VenteProduitExtra enregistrerVente(Integer idVol, Integer idProduitExtra, Integer quantite) {
        // Récupérer le vol pour obtenir la date de départ
        Optional<Vol> volOpt = volRepository.findById(idVol);
        LocalDateTime dateVente = volOpt.map(Vol::getDateHeureDepart).orElse(LocalDateTime.now());

        VenteProduitExtra vente = new VenteProduitExtra();
        vente.setIdVol(idVol);
        vente.setIdProduitExtra(idProduitExtra);
        vente.setQuantite(quantite);
        vente.setDateVente(dateVente);

        return repository.save(vente);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}

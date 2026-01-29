package itu.company.aerienne.dto;

import java.time.LocalDateTime;

public class VenteProduitViewDto {
    private Integer id;
    private String volDescription;
    private String produitNom;
    private Integer quantite;
    private LocalDateTime dateVente;

    public VenteProduitViewDto() {}

    public VenteProduitViewDto(Integer id, String volDescription, String produitNom, Integer quantite, LocalDateTime dateVente) {
        this.id = id;
        this.volDescription = volDescription;
        this.produitNom = produitNom;
        this.quantite = quantite;
        this.dateVente = dateVente;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getVolDescription() { return volDescription; }
    public void setVolDescription(String volDescription) { this.volDescription = volDescription; }

    public String getProduitNom() { return produitNom; }
    public void setProduitNom(String produitNom) { this.produitNom = produitNom; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public LocalDateTime getDateVente() { return dateVente; }
    public void setDateVente(LocalDateTime dateVente) { this.dateVente = dateVente; }
}

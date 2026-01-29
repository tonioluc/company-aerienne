package itu.company.aerienne.dto;

import java.math.BigDecimal;

public class VenteParProduitDto {

    private Integer idProduit;
    private String nomProduit;
    private BigDecimal prixUnitaire;
    private Integer quantiteTotale;
    private BigDecimal montantTotal;

    public VenteParProduitDto() {}

    public VenteParProduitDto(Integer idProduit, String nomProduit, BigDecimal prixUnitaire, Integer quantiteTotale) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.prixUnitaire = prixUnitaire;
        this.quantiteTotale = quantiteTotale;
        this.montantTotal = prixUnitaire.multiply(BigDecimal.valueOf(quantiteTotale));
    }

    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getQuantiteTotale() {
        return quantiteTotale;
    }

    public void setQuantiteTotale(Integer quantiteTotale) {
        this.quantiteTotale = quantiteTotale;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }
}

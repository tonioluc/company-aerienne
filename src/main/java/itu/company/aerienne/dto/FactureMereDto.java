package itu.company.aerienne.dto;

import java.math.BigDecimal;

public class FactureMereDto {
    
    private Integer idFactureMere;
    private Integer idSociete;
    private String nomSociete;
    private Integer mois;
    private Integer annee;
    private BigDecimal montantTotal;
    private BigDecimal montantPaye;
    private BigDecimal montantRestant;
    private String libelle; // Pour affichage dans le select (ex: "Janvier 2026 - Reste: 1400 Ar")

    public FactureMereDto() {
    }

    // Getters et Setters
    public Integer getIdFactureMere() {
        return idFactureMere;
    }

    public void setIdFactureMere(Integer idFactureMere) {
        this.idFactureMere = idFactureMere;
    }

    public Integer getIdSociete() {
        return idSociete;
    }

    public void setIdSociete(Integer idSociete) {
        this.idSociete = idSociete;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public Integer getMois() {
        return mois;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public BigDecimal getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(BigDecimal montantPaye) {
        this.montantPaye = montantPaye;
    }

    public BigDecimal getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(BigDecimal montantRestant) {
        this.montantRestant = montantRestant;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    // Méthode utilitaire pour générer le libellé
    public void generateLibelle() {
        String[] moisNoms = {"", "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", 
                            "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
        String moisNom = (mois != null && mois >= 1 && mois <= 12) ? moisNoms[mois] : "Mois inconnu";
        this.libelle = moisNom + " " + annee + " - Reste: " + montantRestant + " Ar";
    }
}
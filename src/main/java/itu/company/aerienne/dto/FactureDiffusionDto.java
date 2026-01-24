package itu.company.aerienne.dto;

import java.math.BigDecimal;

public class FactureDiffusionDto {
    
    private Integer idFactureDiffusion;
    private Integer idFactureMere;
    private Integer idDiffusionPublicitaire;
    private BigDecimal montant;
    
    // Infos de la diffusion
    private String nomSociete;
    private String infoVol; // Ex: "TNR -> Nosy Be - 20/01/2026"
    private Integer nombreDiffusion;
    private Integer mois;
    private Integer annee;

    public FactureDiffusionDto() {
    }

    public Integer getIdFactureDiffusion() {
        return idFactureDiffusion;
    }

    public void setIdFactureDiffusion(Integer idFactureDiffusion) {
        this.idFactureDiffusion = idFactureDiffusion;
    }

    public Integer getIdFactureMere() {
        return idFactureMere;
    }

    public void setIdFactureMere(Integer idFactureMere) {
        this.idFactureMere = idFactureMere;
    }

    public Integer getIdDiffusionPublicitaire() {
        return idDiffusionPublicitaire;
    }

    public void setIdDiffusionPublicitaire(Integer idDiffusionPublicitaire) {
        this.idDiffusionPublicitaire = idDiffusionPublicitaire;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public String getInfoVol() {
        return infoVol;
    }

    public void setInfoVol(String infoVol) {
        this.infoVol = infoVol;
    }

    public Integer getNombreDiffusion() {
        return nombreDiffusion;
    }

    public void setNombreDiffusion(Integer nombreDiffusion) {
        this.nombreDiffusion = nombreDiffusion;
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
}

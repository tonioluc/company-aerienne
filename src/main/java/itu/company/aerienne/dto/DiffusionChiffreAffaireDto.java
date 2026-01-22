package itu.company.aerienne.dto;

import java.math.BigDecimal;

public class DiffusionChiffreAffaireDto {
    
    private String volDescription; // lieu+dateheure départ -> lieu+dateheure arrivé
    private String nomSociete;
    private Integer idSociete;
    private Integer nombreDiffusion;
    private BigDecimal totalCout;
    private BigDecimal montantPaye;
    private BigDecimal resteAPayer;

    public DiffusionChiffreAffaireDto() {
    }

    public DiffusionChiffreAffaireDto(String volDescription, String nomSociete, Integer idSociete, Integer nombreDiffusion, BigDecimal totalCout, BigDecimal montantPaye, BigDecimal resteAPayer) {
        this.volDescription = volDescription;
        this.nomSociete = nomSociete;
        this.idSociete = idSociete;
        this.nombreDiffusion = nombreDiffusion;
        this.totalCout = totalCout;
        this.montantPaye = montantPaye;
        this.resteAPayer = resteAPayer;
    }

    public String getVolDescription() {
        return volDescription;
    }

    public void setVolDescription(String volDescription) {
        this.volDescription = volDescription;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public Integer getIdSociete() {
        return idSociete;
    }

    public void setIdSociete(Integer idSociete) {
        this.idSociete = idSociete;
    }

    public Integer getNombreDiffusion() {
        return nombreDiffusion;
    }

    public void setNombreDiffusion(Integer nombreDiffusion) {
        this.nombreDiffusion = nombreDiffusion;
    }

    public BigDecimal getTotalCout() {
        return totalCout;
    }

    public void setTotalCout(BigDecimal totalCout) {
        this.totalCout = totalCout;
    }

    public BigDecimal getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(BigDecimal montantPaye) {
        this.montantPaye = montantPaye;
    }

    public BigDecimal getResteAPayer() {
        return resteAPayer;
    }

    public void setResteAPayer(BigDecimal resteAPayer) {
        this.resteAPayer = resteAPayer;
    }
}

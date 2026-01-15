package itu.company.aerienne.dto;

import java.math.BigDecimal;

public class ClassePrixDto {
    private Integer idClassePlace;
    private String libelle;
    private BigDecimal prixUnitaire;
    private BigDecimal valeurMaxParClasse;
    private Integer nbrPlaces;

    public ClassePrixDto() {
    }

    public ClassePrixDto(Integer idClassePlace, String libelle, BigDecimal prixUnitaire, Integer nbrPlaces) {
        this.idClassePlace = idClassePlace;
        this.libelle = libelle;
        this.prixUnitaire = prixUnitaire;
        this.nbrPlaces = nbrPlaces;
        this.valeurMaxParClasse = prixUnitaire.multiply(BigDecimal.valueOf(nbrPlaces));
    }

    public Integer getIdClassePlace() {
        return idClassePlace;
    }

    public void setIdClassePlace(Integer idClassePlace) {
        this.idClassePlace = idClassePlace;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getValeurMaxParClasse() {
        return valeurMaxParClasse;
    }

    public void setValeurMaxParClasse(BigDecimal valeurMaxParClasse) {
        this.valeurMaxParClasse = valeurMaxParClasse;
    }

    public Integer getNbrPlaces() {
        return nbrPlaces;
    }

    public void setNbrPlaces(Integer nbrPlaces) {
        this.nbrPlaces = nbrPlaces;
    }
}

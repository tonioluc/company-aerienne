package itu.company.aerienne.dto;

import java.math.BigDecimal;

public class AchatDetailDto {
    
    private Integer idAchat;
    private String nomClient;
    private Integer nombrePlaces;
    private BigDecimal prixUnitaire;
    private BigDecimal prixTotal;
    private String classeLibelle;

    public AchatDetailDto() {
    }

    public AchatDetailDto(Integer idAchat, String nomClient, Integer nombrePlaces, BigDecimal prixUnitaire) {
        this.idAchat = idAchat;
        this.nomClient = nomClient;
        this.nombrePlaces = nombrePlaces;
        this.prixUnitaire = prixUnitaire;
        this.prixTotal = prixUnitaire != null ? prixUnitaire.multiply(BigDecimal.valueOf(nombrePlaces)) : BigDecimal.ZERO;
    }

    public Integer getIdAchat() {
        return idAchat;
    }

    public void setIdAchat(Integer idAchat) {
        this.idAchat = idAchat;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public Integer getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(Integer nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

    public String getClasseLibelle() {
        return classeLibelle;
    }

    public void setClasseLibelle(String classeLibelle) {
        this.classeLibelle = classeLibelle;
    }
}

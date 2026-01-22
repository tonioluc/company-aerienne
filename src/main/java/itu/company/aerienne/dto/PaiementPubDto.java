package itu.company.aerienne.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaiementPubDto {
    
    private Integer idPaiementPub;
    private LocalDateTime datePaiement;
    private BigDecimal montant;
    private Integer idSociete;
    private String nomSociete;

    public PaiementPubDto() {
    }

    public PaiementPubDto(Integer idPaiementPub, LocalDateTime datePaiement, BigDecimal montant, Integer idSociete, String nomSociete) {
        this.idPaiementPub = idPaiementPub;
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.idSociete = idSociete;
        this.nomSociete = nomSociete;
    }

    public Integer getIdPaiementPub() {
        return idPaiementPub;
    }

    public void setIdPaiementPub(Integer idPaiementPub) {
        this.idPaiementPub = idPaiementPub;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
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
}

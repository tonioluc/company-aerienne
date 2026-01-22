package itu.company.aerienne.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "paiement_pub")
public class PaiementPub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_paiement_pub")
    private Integer idPaiementPub;

    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;

    @Column(name = "montant")
    private BigDecimal montant;

    @Column(name = "Id_societe", nullable = false)
    private Integer idSociete;

    public PaiementPub() {
    }

    public PaiementPub(Integer idPaiementPub, LocalDateTime datePaiement, BigDecimal montant, Integer idSociete) {
        this.idPaiementPub = idPaiementPub;
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.idSociete = idSociete;
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
}

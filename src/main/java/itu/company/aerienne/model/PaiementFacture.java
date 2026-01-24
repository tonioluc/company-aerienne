package itu.company.aerienne.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "paiement_facture")
public class PaiementFacture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_paiement_facture")
    private Integer idPaiementFacture;

    @Column(name = "Id_facture_mere", nullable = false)
    private Integer idFactureMere;

    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;

    @Column(name = "montant_paye")
    private BigDecimal montantPaye;

    public PaiementFacture() {
    }

    public PaiementFacture(Integer idPaiementFacture, Integer idFactureMere, LocalDateTime datePaiement, BigDecimal montantPaye) {
        this.idPaiementFacture = idPaiementFacture;
        this.idFactureMere = idFactureMere;
        this.datePaiement = datePaiement;
        this.montantPaye = montantPaye;
    }

    public Integer getIdPaiementFacture() {
        return idPaiementFacture;
    }

    public void setIdPaiementFacture(Integer idPaiementFacture) {
        this.idPaiementFacture = idPaiementFacture;
    }

    public Integer getIdFactureMere() {
        return idFactureMere;
    }

    public void setIdFactureMere(Integer idFactureMere) {
        this.idFactureMere = idFactureMere;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }

    public BigDecimal getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(BigDecimal montantPaye) {
        this.montantPaye = montantPaye;
    }
}

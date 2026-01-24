package itu.company.aerienne.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "facture_mere")
public class FactureMere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_facture_mere")
    private Integer idFactureMere;

    @Column(name = "Id_societe", nullable = false)
    private Integer idSociete;

    @Column(name = "mois")
    private Integer mois;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "montant_total")
    private BigDecimal montantTotal;

    @Column(name = "montant_paye")
    private BigDecimal montantPaye;

    public FactureMere() {
    }

    public FactureMere(Integer idFactureMere, Integer idSociete, Integer mois, Integer annee, BigDecimal montantTotal, BigDecimal montantPaye) {
        this.idFactureMere = idFactureMere;
        this.idSociete = idSociete;
        this.mois = mois;
        this.annee = annee;
        this.montantTotal = montantTotal;
        this.montantPaye = montantPaye;
    }

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
        BigDecimal total = this.montantTotal != null ? this.montantTotal : BigDecimal.ZERO;
        BigDecimal paye = this.montantPaye != null ? this.montantPaye : BigDecimal.ZERO;
        return total.subtract(paye);
    }
}

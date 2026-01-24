package itu.company.aerienne.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaiementFactureDto {
    
    private Integer idPaiementFacture;
    private Integer idFactureMere;
    private LocalDateTime datePaiement;
    private BigDecimal montantPaye;
    private String nomSociete;
    private Integer mois;
    private Integer annee;
    private BigDecimal montantTotalFacture;
    private BigDecimal montantRestantFacture;

    public PaiementFactureDto() {
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

    public BigDecimal getMontantTotalFacture() {
        return montantTotalFacture;
    }

    public void setMontantTotalFacture(BigDecimal montantTotalFacture) {
        this.montantTotalFacture = montantTotalFacture;
    }

    public BigDecimal getMontantRestantFacture() {
        return montantRestantFacture;
    }

    public void setMontantRestantFacture(BigDecimal montantRestantFacture) {
        this.montantRestantFacture = montantRestantFacture;
    }

    public String getLibelleFacture() {
        String[] moisNoms = {"", "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", 
                             "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
        String moisNom = (mois != null && mois >= 1 && mois <= 12) ? moisNoms[mois] : "";
        return moisNom + " " + (annee != null ? annee : "");
    }
}

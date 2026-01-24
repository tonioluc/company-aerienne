package itu.company.aerienne.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "facture_diffusion")
public class FactureDiffusion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_facture_diffusion")
    private Integer idFactureDiffusion;

    @Column(name = "Id_facture_mere", nullable = false)
    private Integer idFactureMere;

    @Column(name = "Id_diffusion_publicitaire", nullable = false)
    private Integer idDiffusionPublicitaire;

    @Column(name = "montant")
    private BigDecimal montant;

    public FactureDiffusion() {
    }

    public FactureDiffusion(Integer idFactureDiffusion, Integer idFactureMere, Integer idDiffusionPublicitaire, BigDecimal montant) {
        this.idFactureDiffusion = idFactureDiffusion;
        this.idFactureMere = idFactureMere;
        this.idDiffusionPublicitaire = idDiffusionPublicitaire;
        this.montant = montant;
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
}

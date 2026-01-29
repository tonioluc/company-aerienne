package itu.company.aerienne.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produit_extra")
public class ProduitExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_produit_extra")
    private Integer idProduitExtra;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prix_unitaire", nullable = false, precision = 15, scale = 2)
    private BigDecimal prixUnitaire;

    public ProduitExtra() {}

    public ProduitExtra(Integer idProduitExtra, String nom, BigDecimal prixUnitaire) {
        this.idProduitExtra = idProduitExtra;
        this.nom = nom;
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getIdProduitExtra() {
        return idProduitExtra;
    }

    public void setIdProduitExtra(Integer idProduitExtra) {
        this.idProduitExtra = idProduitExtra;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}

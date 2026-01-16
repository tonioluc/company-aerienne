package itu.company.aerienne.model;

import jakarta.persistence.*;

@Entity
@Table(name = "prix_par_categorie")
public class PrixParCategorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_prix_categorie")
    private Integer idPrixCategorie;

    @Column(name = "Id_categorie_personne", nullable = false)
    private Integer idCategoriePersonne;

    @Column(name = "Id_classe_place", nullable = false)
    private Integer idClassePlace;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "pourcentage")
    private Double pourcentage;

    public PrixParCategorie() {
    }

    public PrixParCategorie(Integer idPrixCategorie, Integer idCategoriePersonne, Integer idClassePlace, Double prix, Double pourcentage) {
        this.idPrixCategorie = idPrixCategorie;
        this.idCategoriePersonne = idCategoriePersonne;
        this.idClassePlace = idClassePlace;
        this.prix = prix;
        this.pourcentage = pourcentage;
    }

    public Integer getIdPrixCategorie() {
        return idPrixCategorie;
    }

    public void setIdPrixCategorie(Integer idPrixCategorie) {
        this.idPrixCategorie = idPrixCategorie;
    }

    public Integer getIdCategoriePersonne() {
        return idCategoriePersonne;
    }

    public void setIdCategoriePersonne(Integer idCategoriePersonne) {
        this.idCategoriePersonne = idCategoriePersonne;
    }

    public Integer getIdClassePlace() {
        return idClassePlace;
    }

    public void setIdClassePlace(Integer idClassePlace) {
        this.idClassePlace = idClassePlace;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(Double pourcentage) {
        this.pourcentage = pourcentage;
    }
}
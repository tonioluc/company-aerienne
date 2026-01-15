package itu.company.aerienne.model;

import jakarta.persistence.*;

@Entity
@Table(name = "classe_place")
public class ClassePlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_classe_place")
    private Integer idClassePlace;

    @Column(name = "libelle", nullable = false, length = 50)
    private String libelle;

    public ClassePlace() {
    }

    public ClassePlace(Integer idClassePlace, String libelle) {
        this.idClassePlace = idClassePlace;
        this.libelle = libelle;
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
}
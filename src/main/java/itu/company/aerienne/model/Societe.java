package itu.company.aerienne.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Societe")
public class Societe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_societe")
    private Integer idSociete;

    @Column(name = "nom")
    private String nom;

    public Societe() {
    }
    public Societe(Integer idSociete, String nom) {
        this.idSociete = idSociete;
        this.nom = nom;
    }

    public Integer getIdSociete() {
        return idSociete;
    }
    public void setIdSociete(Integer idSociete) {
        this.idSociete = idSociete;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    
}

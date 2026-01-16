package itu.company.aerienne.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Categorie_client")
public class CategorieClient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IdCategorieClient;

    @Column(nullable = false, length = 50)
    private String libelle;

    public CategorieClient() {
    }

    public CategorieClient(Integer idCategorieClient, String libelle) {
        IdCategorieClient = idCategorieClient;
        this.libelle = libelle;
    }

    public Integer getIdCategorieClient() {
        return IdCategorieClient;
    }

    public void setIdCategorieClient(Integer idCategorieClient) {
        IdCategorieClient = idCategorieClient;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}

package itu.company.aerienne.model;

import jakarta.persistence.*;

@Entity
@Table(name = "avions")
public class Avion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String modele;

    @Column(nullable = false)
    private Integer capacite;

    public Avion() {
    }

    public Avion(Integer id, String modele, Integer capacite) {
        this.id = id;
        this.modele = modele;
        this.capacite = capacite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }
}

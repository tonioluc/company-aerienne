package itu.company.aerienne.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Avion")
public class Avion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id_avion;

    @Column(nullable = false, length = 50)
    private String modele;

    @Column(nullable = false)
    private Integer capacite;

    public Avion() {
    }

    public Avion(Integer Id_avion, String modele, Integer capacite) {
        this.Id_avion = Id_avion;
        this.modele = modele;
        this.capacite = capacite;
    }

    public Integer getId_avion() {
        return Id_avion;
    }

    public void setId_avion(Integer Id_avion) {
        this.Id_avion = Id_avion;
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

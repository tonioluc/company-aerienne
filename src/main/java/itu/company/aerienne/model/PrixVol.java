package itu.company.aerienne.model;

import jakarta.persistence.*;

@Entity
@Table(name = "prix_vol")
public class PrixVol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_prix_vol")
    private Integer idPrixVol;

    @Column(name = "Id_vol", nullable = false)
    private Integer idVol;

    @Column(name = "Id_classe_place", nullable = false)
    private Integer idClassePlace;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "nbr_places")
    private Integer nbrPlaces;

    public PrixVol() {
    }

    public PrixVol(Integer idPrixVol, Integer idVol, Integer idClassePlace, Double prix, Integer nbrPlaces) {
        this.idPrixVol = idPrixVol;
        this.idVol = idVol;
        this.idClassePlace = idClassePlace;
        this.prix = prix;
        this.nbrPlaces = nbrPlaces;
    }

    public Integer getIdPrixVol() {
        return idPrixVol;
    }

    public void setIdPrixVol(Integer idPrixVol) {
        this.idPrixVol = idPrixVol;
    }

    public Integer getIdVol() {
        return idVol;
    }

    public void setIdVol(Integer idVol) {
        this.idVol = idVol;
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

    public Integer getNbrPlaces() {
        return nbrPlaces;
    }

    public void setNbrPlaces(Integer nbrPlaces) {
        this.nbrPlaces = nbrPlaces;
    }
    
}

package itu.company.aerienne.model;

import jakarta.persistence.*;

@Entity
@Table(name = "achat_places")
public class AchatPlaces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_achat_places")
    private Integer idAchatPlaces;

    @Column(name = "nom_client", length = 50)
    private String nomClient;

    @Column(name = "nombre_place", length = 50)
    private String nombrePlace;

    @Column(name = "Id_vol", nullable = false)
    private Integer idVol;

    public AchatPlaces() {
    }

    public AchatPlaces(Integer idAchatPlaces, String nomClient, String nombrePlace, Integer idVol) {
        this.idAchatPlaces = idAchatPlaces;
        this.nomClient = nomClient;
        this.nombrePlace = nombrePlace;
        this.idVol = idVol;
    }

    public Integer getIdAchatPlaces() {
        return idAchatPlaces;
    }

    public void setIdAchatPlaces(Integer idAchatPlaces) {
        this.idAchatPlaces = idAchatPlaces;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getNombrePlace() {
        return nombrePlace;
    }

    public void setNombrePlace(String nombrePlace) {
        this.nombrePlace = nombrePlace;
    }

    public Integer getIdVol() {
        return idVol;
    }

    public void setIdVol(Integer idVol) {
        this.idVol = idVol;
    }
}

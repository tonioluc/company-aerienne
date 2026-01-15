package itu.company.aerienne.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trajet")
public class Trajet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_trajet")
    private Integer idTrajet;

    @Column(name = "Id_aeroport_depart", nullable = false)
    private Integer idAeroportDepart;

    @Column(name = "Id_aeroport_arrive", nullable = false)
    private Integer idAeroportArrive;

    public Trajet() {
    }

    public Trajet(Integer idTrajet, String numeroVol, Integer idAeroportDepart, Integer idAeroportArrive) {
        this.idTrajet = idTrajet;
        this.idAeroportDepart = idAeroportDepart;
        this.idAeroportArrive = idAeroportArrive;
    }

    public Integer getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(Integer idTrajet) {
        this.idTrajet = idTrajet;
    }

    public Integer getIdAeroportDepart() {
        return idAeroportDepart;
    }

    public void setIdAeroportDepart(Integer idAeroportDepart) {
        this.idAeroportDepart = idAeroportDepart;
    }

    public Integer getIdAeroportArrive() {
        return idAeroportArrive;
    }

    public void setIdAeroportArrive(Integer idAeroportArrive) {
        this.idAeroportArrive = idAeroportArrive;
    }
}
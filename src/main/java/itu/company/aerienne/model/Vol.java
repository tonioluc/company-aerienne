package itu.company.aerienne.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vol")
public class Vol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_vol")
    private Integer idVol;

    @Column(name = "date_heure_depart")
    private LocalDateTime dateHeureDepart;

    @Column(name = "date_heure_arrive")
    private LocalDateTime dateHeureArrive;

    @Column(name = "Id_avion", nullable = false)
    private Integer idAvion;

    @Column(name = "Id_trajet", nullable = false)
    private Integer idTrajet;

    public Vol() {
    }

    public Vol(Integer idVol, LocalDateTime dateHeureDepart, LocalDateTime dateHeureArrive, Integer idAvion, Integer idTrajet) {
        this.idVol = idVol;
        this.dateHeureDepart = dateHeureDepart;
        this.dateHeureArrive = dateHeureArrive;
        this.idAvion = idAvion;
        this.idTrajet = idTrajet;
    }

    public Integer getIdVol() {
        return idVol;
    }

    public void setIdVol(Integer idVol) {
        this.idVol = idVol;
    }

    public LocalDateTime getDateHeureDepart() {
        return dateHeureDepart;
    }

    public void setDateHeureDepart(LocalDateTime dateHeureDepart) {
        this.dateHeureDepart = dateHeureDepart;
    }

    public LocalDateTime getDateHeureArrive() {
        return dateHeureArrive;
    }

    public void setDateHeureArrive(LocalDateTime dateHeureArrive) {
        this.dateHeureArrive = dateHeureArrive;
    }

    public Integer getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(Integer idTrajet) {
        this.idTrajet = idTrajet;
    }

    public Integer getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(Integer idAvion) {
        this.idAvion = idAvion;
    }
}

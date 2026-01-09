package itu.company.aerienne.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
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

    @Column(name = "prix_place")
    private BigDecimal prixPlace;

    @Column(name = "Id_modele_vol", nullable = false)
    private Integer idModeleVol;

    public Vol() {
    }

    public Vol(Integer idVol, LocalDateTime dateHeureDepart, LocalDateTime dateHeureArrive, BigDecimal prixPlace, Integer idModeleVol) {
        this.idVol = idVol;
        this.dateHeureDepart = dateHeureDepart;
        this.dateHeureArrive = dateHeureArrive;
        this.prixPlace = prixPlace;
        this.idModeleVol = idModeleVol;
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

    public BigDecimal getPrixPlace() {
        return prixPlace;
    }

    public void setPrixPlace(BigDecimal prixPlace) {
        this.prixPlace = prixPlace;
    }

    public Integer getIdModeleVol() {
        return idModeleVol;
    }

    public void setIdModeleVol(Integer idModeleVol) {
        this.idModeleVol = idModeleVol;
    }
}

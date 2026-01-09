package itu.company.aerienne.model;

import jakarta.persistence.*;

@Entity
@Table(name = "modele_vol")
public class ModeleVol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_modele_vol")
    private Integer idModeleVol;

    @Column(name = "numero_vol", length = 50)
    private String numeroVol;

    @Column(name = "Id_aeroport_depart", nullable = false)
    private Integer idAeroportDepart;

    @Column(name = "Id_aeroport_arrive", nullable = false)
    private Integer idAeroportArrive;

    public ModeleVol() {
    }

    public ModeleVol(Integer idModeleVol, String numeroVol, Integer idAeroportDepart, Integer idAeroportArrive) {
        this.idModeleVol = idModeleVol;
        this.numeroVol = numeroVol;
        this.idAeroportDepart = idAeroportDepart;
        this.idAeroportArrive = idAeroportArrive;
    }

    public Integer getIdModeleVol() {
        return idModeleVol;
    }

    public void setIdModeleVol(Integer idModeleVol) {
        this.idModeleVol = idModeleVol;
    }

    public String getNumeroVol() {
        return numeroVol;
    }

    public void setNumeroVol(String numeroVol) {
        this.numeroVol = numeroVol;
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
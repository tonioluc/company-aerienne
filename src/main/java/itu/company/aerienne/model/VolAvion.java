package itu.company.aerienne.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vol_avion")
@IdClass(VolAvionId.class)
public class VolAvion {

    @Id
    @Column(name = "Id_avion")
    private Integer idAvion;

    @Id
    @Column(name = "Id_vol")
    private Integer idVol;

    @Column(name = "numero")
    private Integer numero;

    public VolAvion() {
    }

    public VolAvion(Integer idAvion, Integer idVol, Integer numero) {
        this.idAvion = idAvion;
        this.idVol = idVol;
        this.numero = numero;
    }

    public Integer getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(Integer idAvion) {
        this.idAvion = idAvion;
    }

    public Integer getIdVol() {
        return idVol;
    }

    public void setIdVol(Integer idVol) {
        this.idVol = idVol;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}

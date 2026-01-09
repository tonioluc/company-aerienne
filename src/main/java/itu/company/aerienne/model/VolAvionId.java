package itu.company.aerienne.model;

import java.io.Serializable;
import java.util.Objects;

public class VolAvionId implements Serializable {
    private Integer idAvion;
    private Integer idVol;

    public VolAvionId() {}

    public VolAvionId(Integer idAvion, Integer idVol) {
        this.idAvion = idAvion;
        this.idVol = idVol;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolAvionId that = (VolAvionId) o;
        return Objects.equals(idAvion, that.idAvion) && Objects.equals(idVol, that.idVol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAvion, idVol);
    }
}

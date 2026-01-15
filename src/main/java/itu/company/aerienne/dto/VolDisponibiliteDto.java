package itu.company.aerienne.dto;

import itu.company.aerienne.model.Vol;
import java.util.List;

public class VolDisponibiliteDto {
    private Vol vol;
    private List<ClasseDisponibiliteDto> disponibilites;

    public VolDisponibiliteDto() {}

    public VolDisponibiliteDto(Vol vol, List<ClasseDisponibiliteDto> disponibilites) {
        this.vol = vol;
        this.disponibilites = disponibilites;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public List<ClasseDisponibiliteDto> getDisponibilites() {
        return disponibilites;
    }

    public void setDisponibilites(List<ClasseDisponibiliteDto> disponibilites) {
        this.disponibilites = disponibilites;
    }
}

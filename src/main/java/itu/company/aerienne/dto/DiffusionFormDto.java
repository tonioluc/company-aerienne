package itu.company.aerienne.dto;

public class DiffusionFormDto {
    
    private Integer societe;
    private Integer vol;
    private Integer nombreDiffusion;

    public DiffusionFormDto() {
    }

    public DiffusionFormDto(Integer societe, Integer vol, Integer nombreDiffusion) {
        this.societe = societe;
        this.vol = vol;
        this.nombreDiffusion = nombreDiffusion;
    }

    public Integer getSociete() {
        return societe;
    }

    public void setSociete(Integer societe) {
        this.societe = societe;
    }

    public Integer getVol() {
        return vol;
    }

    public void setVol(Integer vol) {
        this.vol = vol;
    }

    public Integer getNombreDiffusion() {
        return nombreDiffusion;
    }

    public void setNombreDiffusion(Integer nombreDiffusion) {
        this.nombreDiffusion = nombreDiffusion;
    }
}

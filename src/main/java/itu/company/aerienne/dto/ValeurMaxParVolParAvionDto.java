package itu.company.aerienne.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ValeurMaxParVolParAvionDto {
    private Integer idAvion;
    private String modele;
    private BigDecimal capaciteMax;
    private List<ValeurMaxVolDto> vols;
    private BigDecimal valeurMaxTotale;

    public ValeurMaxParVolParAvionDto() {
        this.vols = new ArrayList<>();
        this.valeurMaxTotale = BigDecimal.ZERO;
    }

    public ValeurMaxParVolParAvionDto(Integer idAvion, String modele, BigDecimal capaciteMax) {
        this.idAvion = idAvion;
        this.modele = modele;
        this.capaciteMax = capaciteMax;
        this.vols = new ArrayList<>();
        this.valeurMaxTotale = BigDecimal.ZERO;
    }

    public void addVol(ValeurMaxVolDto vol) {
        this.vols.add(vol);
        this.valeurMaxTotale = this.valeurMaxTotale.add(vol.getValeurMax());
    }

    public Integer getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(Integer idAvion) {
        this.idAvion = idAvion;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public BigDecimal getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(BigDecimal capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public List<ValeurMaxVolDto> getVols() {
        return vols;
    }

    public void setVols(List<ValeurMaxVolDto> vols) {
        this.vols = vols;
        // Recalculer valeurMaxTotale
        this.valeurMaxTotale = BigDecimal.ZERO;
        for (ValeurMaxVolDto v : vols) {
            this.valeurMaxTotale = this.valeurMaxTotale.add(v.getValeurMax());
        }
    }

    public BigDecimal getValeurMaxTotale() {
        return valeurMaxTotale;
    }

    public void setValeurMaxTotale(BigDecimal valeurMaxTotale) {
        this.valeurMaxTotale = valeurMaxTotale;
    }
}

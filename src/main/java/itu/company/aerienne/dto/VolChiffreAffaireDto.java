package itu.company.aerienne.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VolChiffreAffaireDto {
    
    private Integer idVol;
    private String dateHeureDepart;
    private String dateHeureArrive;
    private Integer totalPlacesVendues;
    private BigDecimal chiffreAffaires;
    private List<AchatDetailDto> achats;

    public VolChiffreAffaireDto() {
        this.achats = new ArrayList<>();
        this.totalPlacesVendues = 0;
        this.chiffreAffaires = BigDecimal.ZERO;
    }

    public Integer getIdVol() {
        return idVol;
    }

    public void setIdVol(Integer idVol) {
        this.idVol = idVol;
    }

    public String getDateHeureDepart() {
        return dateHeureDepart;
    }

    public void setDateHeureDepart(String dateHeureDepart) {
        this.dateHeureDepart = dateHeureDepart;
    }

    public String getDateHeureArrive() {
        return dateHeureArrive;
    }

    public void setDateHeureArrive(String dateHeureArrive) {
        this.dateHeureArrive = dateHeureArrive;
    }

    public Integer getTotalPlacesVendues() {
        return totalPlacesVendues;
    }

    public void setTotalPlacesVendues(Integer totalPlacesVendues) {
        this.totalPlacesVendues = totalPlacesVendues;
    }

    public BigDecimal getChiffreAffaires() {
        return chiffreAffaires;
    }

    public void setChiffreAffaires(BigDecimal chiffreAffaires) {
        this.chiffreAffaires = chiffreAffaires;
    }

    public List<AchatDetailDto> getAchats() {
        return achats;
    }

    public void setAchats(List<AchatDetailDto> achats) {
        this.achats = achats;
    }

    public void addAchat(AchatDetailDto achat) {
        this.achats.add(achat);
    }
}

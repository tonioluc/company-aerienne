package itu.company.aerienne.dto;

import java.math.BigDecimal;

public class ChiffreAffaireDto {
    
    private Integer totalPlacesVendues;
    private BigDecimal chiffreAffaires;

    public ChiffreAffaireDto() {
        this.totalPlacesVendues = 0;
        this.chiffreAffaires = BigDecimal.ZERO;
    }

    public ChiffreAffaireDto(Integer totalPlacesVendues, BigDecimal chiffreAffaires) {
        this.totalPlacesVendues = totalPlacesVendues != null ? totalPlacesVendues : 0;
        this.chiffreAffaires = chiffreAffaires != null ? chiffreAffaires : BigDecimal.ZERO;
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
}

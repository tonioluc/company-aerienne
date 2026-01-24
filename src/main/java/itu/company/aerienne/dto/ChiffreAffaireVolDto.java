package itu.company.aerienne.dto;

import java.math.BigDecimal;

public class ChiffreAffaireVolDto {
    
    private VolInfoDto vol;
    private BigDecimal montantTicketVendu;
    private BigDecimal montantPublicite;
    private BigDecimal montantTotal;
    private BigDecimal montantAPayer;
    private BigDecimal montantResteAPayer;

    public ChiffreAffaireVolDto() {
        this.montantTicketVendu = BigDecimal.ZERO;
        this.montantPublicite = BigDecimal.ZERO;
        this.montantTotal = BigDecimal.ZERO;
        this.montantAPayer = BigDecimal.ZERO;
        this.montantResteAPayer = BigDecimal.ZERO;
    }

    public VolInfoDto getVol() {
        return vol;
    }

    public void setVol(VolInfoDto vol) {
        this.vol = vol;
    }

    public BigDecimal getMontantTicketVendu() {
        return montantTicketVendu;
    }

    public void setMontantTicketVendu(BigDecimal montantTicketVendu) {
        this.montantTicketVendu = montantTicketVendu;
    }

    public BigDecimal getMontantPublicite() {
        return montantPublicite;
    }

    public void setMontantPublicite(BigDecimal montantPublicite) {
        this.montantPublicite = montantPublicite;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public BigDecimal getMontantAPayer() {
        return montantAPayer;
    }

    public void setMontantAPayer(BigDecimal montantAPayer) {
        this.montantAPayer = montantAPayer;
    }

    public BigDecimal getMontantResteAPayer() {
        return montantResteAPayer;
    }

    public void setMontantResteAPayer(BigDecimal montantResteAPayer) {
        this.montantResteAPayer = montantResteAPayer;
    }

    public void calculateTotals() {
        this.montantTotal = this.montantTicketVendu.add(this.montantPublicite);
        this.montantResteAPayer = this.montantPublicite.subtract(this.montantAPayer);
    }
}

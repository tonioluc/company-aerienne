package itu.company.aerienne.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ChiffreAffaireVolDto {
    
    private VolInfoDto vol;
    private BigDecimal montantTicketVendu;
    private BigDecimal montantPublicite;
    private BigDecimal montantProduitExtra;
    private BigDecimal montantTotal;
    private BigDecimal montantAPayer;
    private BigDecimal montantResteAPayer;
    private List<ProduitVenduDetailDto> produitsVendus;

    public ChiffreAffaireVolDto() {
        this.montantTicketVendu = BigDecimal.ZERO;
        this.montantPublicite = BigDecimal.ZERO;
        this.montantProduitExtra = BigDecimal.ZERO;
        this.montantTotal = BigDecimal.ZERO;
        this.montantAPayer = BigDecimal.ZERO;
        this.montantResteAPayer = BigDecimal.ZERO;
        this.produitsVendus = new ArrayList<>();
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

    public BigDecimal getMontantProduitExtra() {
        return montantProduitExtra;
    }

    public void setMontantProduitExtra(BigDecimal montantProduitExtra) {
        this.montantProduitExtra = montantProduitExtra;
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

    public List<ProduitVenduDetailDto> getProduitsVendus() {
        return produitsVendus;
    }

    public void setProduitsVendus(List<ProduitVenduDetailDto> produitsVendus) {
        this.produitsVendus = produitsVendus;
    }

    public void calculateTotals() {
        this.montantTotal = this.montantTicketVendu.add(this.montantPublicite).add(this.montantProduitExtra);
        this.montantResteAPayer = this.montantPublicite.subtract(this.montantAPayer);
    }
}

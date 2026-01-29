package itu.company.aerienne.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vente_produit_extra")
public class VenteProduitExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_vente_produit_extra")
    private Integer idVenteProduitExtra;

    @Column(name = "Id_vol", nullable = false)
    private Integer idVol;

    @Column(name = "Id_produit_extra", nullable = false)
    private Integer idProduitExtra;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "date_vente")
    private LocalDateTime dateVente;

    public VenteProduitExtra() {}

    public VenteProduitExtra(Integer idVol, Integer idProduitExtra, Integer quantite, LocalDateTime dateVente) {
        this.idVol = idVol;
        this.idProduitExtra = idProduitExtra;
        this.quantite = quantite;
        this.dateVente = dateVente;
    }

    public Integer getIdVenteProduitExtra() {
        return idVenteProduitExtra;
    }

    public void setIdVenteProduitExtra(Integer idVenteProduitExtra) {
        this.idVenteProduitExtra = idVenteProduitExtra;
    }

    public Integer getIdVol() {
        return idVol;
    }

    public void setIdVol(Integer idVol) {
        this.idVol = idVol;
    }

    public Integer getIdProduitExtra() {
        return idProduitExtra;
    }

    public void setIdProduitExtra(Integer idProduitExtra) {
        this.idProduitExtra = idProduitExtra;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public LocalDateTime getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDateTime dateVente) {
        this.dateVente = dateVente;
    }
}

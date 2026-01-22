package itu.company.aerienne.model;

import jakarta.persistence.*;

@Entity
@Table(name = "diffusion_publicitaire")
public class DiffusionPublicitaire {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_diffusion_publicitaire")
    private Integer idDiffusionPublicitaire;

    @Column(name = "Id_societe", nullable = false)
    private Integer idSociete;

    @Column(name = "Id_vol", nullable = false)
    private Integer idVol;

    @Column(name = "mois")
    private Integer mois;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "nombre_diffusion")
    private Integer nombreDiffusion;

    public DiffusionPublicitaire() {
    }

    public DiffusionPublicitaire(Integer idDiffusionPublicitaire, Integer idSociete, Integer idVol, Integer mois, Integer annee, Integer nombreDiffusion) {
        this.idDiffusionPublicitaire = idDiffusionPublicitaire;
        this.idSociete = idSociete;
        this.idVol = idVol;
        this.mois = mois;
        this.annee = annee;
        this.nombreDiffusion = nombreDiffusion;
    }

    public Integer getIdDiffusionPublicitaire() {
        return idDiffusionPublicitaire;
    }

    public void setIdDiffusionPublicitaire(Integer idDiffusionPublicitaire) {
        this.idDiffusionPublicitaire = idDiffusionPublicitaire;
    }

    public Integer getIdSociete() {
        return idSociete;
    }
    public void setIdSociete(Integer idSociete) {
        this.idSociete = idSociete;
    }
    public Integer getIdVol() {
        return idVol;
    }
    public void setIdVol(Integer idVol) {
        this.idVol = idVol;
    }
    public Integer getMois() {
        return mois;
    }
    public void setMois(Integer mois) {
        this.mois = mois;
    }
    public Integer getAnnee() {
        return annee;
    }
    public void setAnnee(Integer annee) {
        this.annee = annee;
    }
    public Integer getNombreDiffusion() {
        return nombreDiffusion;
    }
    public void setNombreDiffusion(Integer nombreDiffusion) {
        this.nombreDiffusion = nombreDiffusion;
    }

}

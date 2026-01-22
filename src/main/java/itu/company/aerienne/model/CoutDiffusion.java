package itu.company.aerienne.model;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "cout_diffusion")
public class CoutDiffusion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_cout_diffusion")
    private Integer idCoutDiffusion;

    @Column(name = "cout_unitaire")
    private BigDecimal coutUnitaire;

    public CoutDiffusion() {
    }
    public CoutDiffusion(Integer idCoutDiffusion, BigDecimal coutUnitaire) {
        this.idCoutDiffusion = idCoutDiffusion;
        this.coutUnitaire = coutUnitaire;
    }

    public Integer getIdCoutDiffusion() {
        return idCoutDiffusion;
    }
    public void setIdCoutDiffusion(Integer idCoutDiffusion) {
        this.idCoutDiffusion = idCoutDiffusion;
    }
    
    public BigDecimal getCoutUnitaire() {
        return coutUnitaire;
    }
    public void setCoutUnitaire(BigDecimal coutUnitaire) {
        this.coutUnitaire = coutUnitaire;
    }
}

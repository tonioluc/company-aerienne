package itu.company.aerienne.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ValeurMaxVolDto {
    private Integer idVol;
    private String depart;  // lieu + date_heure
    private String arrive;  // lieu + date_heure
    private List<ClassePrixDto> classesPrix;
    private BigDecimal valeurMax;

    public ValeurMaxVolDto() {
        this.classesPrix = new ArrayList<>();
        this.valeurMax = BigDecimal.ZERO;
    }

    public ValeurMaxVolDto(Integer idVol, String depart, String arrive) {
        this.idVol = idVol;
        this.depart = depart;
        this.arrive = arrive;
        this.classesPrix = new ArrayList<>();
        this.valeurMax = BigDecimal.ZERO;
    }

    public void addClassePrix(ClassePrixDto classePrix) {
        this.classesPrix.add(classePrix);
        this.valeurMax = this.valeurMax.add(classePrix.getValeurMaxParClasse());
    }

    public Integer getIdVol() {
        return idVol;
    }

    public void setIdVol(Integer idVol) {
        this.idVol = idVol;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public List<ClassePrixDto> getClassesPrix() {
        return classesPrix;
    }

    public void setClassesPrix(List<ClassePrixDto> classesPrix) {
        this.classesPrix = classesPrix;
        // Recalculer valeurMax
        this.valeurMax = BigDecimal.ZERO;
        for (ClassePrixDto cp : classesPrix) {
            this.valeurMax = this.valeurMax.add(cp.getValeurMaxParClasse());
        }
    }

    public BigDecimal getValeurMax() {
        return valeurMax;
    }

    public void setValeurMax(BigDecimal valeurMax) {
        this.valeurMax = valeurMax;
    }
}

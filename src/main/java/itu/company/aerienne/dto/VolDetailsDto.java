package itu.company.aerienne.dto;

import java.util.ArrayList;
import java.util.List;

public class VolDetailsDto {
    private Integer idVol;
    private String dateHeureDepart;
    private String dateHeureArrive;
    private String trajetDescription; // depart -> arrive
    private String avionModele;
    private Integer avionCapacite;
    private List<ClassePrixDto> classes = new ArrayList<>();

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

    public String getTrajetDescription() {
        return trajetDescription;
    }

    public void setTrajetDescription(String trajetDescription) {
        this.trajetDescription = trajetDescription;
    }

    public String getAvionModele() {
        return avionModele;
    }

    public void setAvionModele(String avionModele) {
        this.avionModele = avionModele;
    }

    public Integer getAvionCapacite() {
        return avionCapacite;
    }

    public void setAvionCapacite(Integer avionCapacite) {
        this.avionCapacite = avionCapacite;
    }

    public List<ClassePrixDto> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassePrixDto> classes) {
        this.classes = classes;
    }

    public void addClasse(ClassePrixDto c) {
        this.classes.add(c);
    }
}

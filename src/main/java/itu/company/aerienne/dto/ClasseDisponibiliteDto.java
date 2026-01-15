package itu.company.aerienne.dto;

public class ClasseDisponibiliteDto {
    private Integer idClassePlace;
    private String libelle;
    private Integer placesDisponibles;

    public ClasseDisponibiliteDto() {}

    public ClasseDisponibiliteDto(Integer idClassePlace, String libelle, Integer placesDisponibles) {
        this.idClassePlace = idClassePlace;
        this.libelle = libelle;
        this.placesDisponibles = placesDisponibles;
    }

    public Integer getIdClassePlace() {
        return idClassePlace;
    }

    public void setIdClassePlace(Integer idClassePlace) {
        this.idClassePlace = idClassePlace;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getPlacesDisponibles() {
        return placesDisponibles;
    }

    public void setPlacesDisponibles(Integer placesDisponibles) {
        this.placesDisponibles = placesDisponibles;
    }
}

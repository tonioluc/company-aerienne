package itu.company.aerienne.dto;

public class AchatPlacesFormDto {
    private String clientNomComplet;
    private Integer aeroportDepartId;
    private Integer aeroportArriveId;
    private Integer places;
    private Integer idClassePlace;

    public String getClientNomComplet() {
        return clientNomComplet;
    }

    public void setClientNomComplet(String clientNomComplet) {
        this.clientNomComplet = clientNomComplet;
    }

    public Integer getAeroportDepartId() {
        return aeroportDepartId;
    }

    public void setAeroportDepartId(Integer aeroportDepartId) {
        this.aeroportDepartId = aeroportDepartId;
    }

    public Integer getAeroportArriveId() {
        return aeroportArriveId;
    }

    public void setAeroportArriveId(Integer aeroportArriveId) {
        this.aeroportArriveId = aeroportArriveId;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public Integer getIdClassePlace() {
        return idClassePlace;
    }

    public void setIdClassePlace(Integer idClassePlace) {
        this.idClassePlace = idClassePlace;
    }

    /**
     * Vérifie que l'aéroport de départ et d'arrivée sont différents.
     * @throws IllegalArgumentException si les deux aéroports sont identiques
     */
    public void estAeroportDepartArriveDifferent() throws IllegalArgumentException {
        if (aeroportDepartId != null && aeroportDepartId.equals(aeroportArriveId)) {
            throw new IllegalArgumentException("L'aéroport de départ et d'arrivée doivent être différents.");
        }
    }
}

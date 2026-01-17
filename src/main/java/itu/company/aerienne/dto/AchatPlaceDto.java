package itu.company.aerienne.dto;

public class AchatPlaceDto {
	private Integer idAchatPlaces;
	private String nomClient;
	private Integer nombrePlace;
	private String classeLibelle;
	private String categorieLibelle;
	private String volDescription;

	public Integer getIdAchatPlaces() {
		return idAchatPlaces;
	}

	public void setIdAchatPlaces(Integer idAchatPlaces) {
		this.idAchatPlaces = idAchatPlaces;
	}

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public Integer getNombrePlace() {
		return nombrePlace;
	}

	public void setNombrePlace(Integer nombrePlace) {
		this.nombrePlace = nombrePlace;
	}

	public String getClasseLibelle() {
		return classeLibelle;
	}

	public void setClasseLibelle(String classeLibelle) {
		this.classeLibelle = classeLibelle;
	}

	public String getCategorieLibelle() {
		return categorieLibelle;
	}

	public void setCategorieLibelle(String categorieLibelle) {
		this.categorieLibelle = categorieLibelle;
	}

	public String getVolDescription() {
		return volDescription;
	}

	public void setVolDescription(String volDescription) {
		this.volDescription = volDescription;
	}
}

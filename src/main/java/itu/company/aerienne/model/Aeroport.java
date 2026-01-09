package itu.company.aerienne.model;

import jakarta.persistence.*;

@Entity
@Table(name = "aeroport")
public class Aeroport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_aeroport")
    private Integer idAeroport; 

    @Column(name = "code_IATA", length = 50)
    private String codeIATA;

    @Column(name = "ville", length = 100)
    private String ville;

    public Aeroport() {
    }

    public Aeroport(Integer idAeroport, String codeIATA, String ville) {
        this.idAeroport = idAeroport;
        this.codeIATA = codeIATA;
        this.ville = ville;
    }

    public Integer getIdAeroport() {
        return idAeroport;
    }

    public void setIdAeroport(Integer idAeroport) {
        this.idAeroport = idAeroport;
    }

    public String getCodeIATA() {
        return codeIATA;
    }

    public void setCodeIATA(String codeIATA) {
        this.codeIATA = codeIATA;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}   
package Rest.Models;

import jakarta.persistence.*;

@Entity
public class Adresse {

/* Attributs */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pays;
    private String ville;
    private String rue;
    private String lieuDit;
    private Integer positionGPS;

/* Constructeur */

    public Adresse() {}

    public Adresse(String pays, String ville, String rue, String lieuDit, Integer positionGPS) {
        this.pays = pays;
        this.ville = ville;
        this.rue = rue;
        this.lieuDit = lieuDit;
        this.positionGPS = positionGPS;
    }

/* Getters & Setters */

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPays() {
        return pays;
    }
    public void setPays(String pays) {
        this.pays = pays;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public String getRue() {
        return rue;
    }
    public void setRue(String rue) {
        this.rue = rue;
    }
    public String getLieuDit() {
        return lieuDit;
    }
    public void setLieuDit(String lieuDit) {
        this.lieuDit = lieuDit;
    }
    public Integer getPositionGPS() {
        return positionGPS;
    }
    public void setPositionGPS(Integer positionGPS) {
        this.positionGPS = positionGPS;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "id=" + id +
                ", pays='" + pays + '\'' +
                ", ville='" + ville + '\'' +
                ", rue='" + rue + '\'' +
                ", lieuDit='" + lieuDit + '\'' +
                ", positionGPS=" + positionGPS +
                '}';
    }

}

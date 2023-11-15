package Rest.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Ajoutez une clé primaire pour l'entité Hotel

    private String nom;
    private String categorie;

    @OneToOne(cascade = CascadeType.ALL)  // Assurez-vous de définir correctement le type de relation avec Adresse
    private Adresse adresse;

    private Integer nbEtoiles;

    @ManyToOne
    @JoinColumn(name = "agence_id")
    private Agence agence;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)  // Assurez-vous de définir correctement le type de relation avec Chambre
    private List<Chambre> chambres;

    public Hotel() {
        this.chambres = new ArrayList<>();
    }

    public Hotel(String nom, String categorie, Adresse adresse, Integer nbEtoiles) {
        this.nom = nom;
        this.categorie = categorie;
        this.adresse = adresse;
        this.nbEtoiles = nbEtoiles;
        this.chambres = new ArrayList<>();
    }

    public void ajouterChambre(Chambre chambre) {
        chambres.add(chambre);
        chambre.setHotel(this);  // Assurez-vous de maintenir la cohérence des relations bidirectionnelles
    }

    public List<Chambre> getChambres() {
        return chambres;
    }

    public String getNom() {
        return nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public Integer getNbEtoiles() {
        return nbEtoiles;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", categorie='" + categorie + '\'' +
                ", adresse=" + adresse +
                ", nbEtoiles=" + nbEtoiles +
                ", nombreChambres=" + chambres.size() +
                '}';
    }


}

package Rest.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String motDePasse;

    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Hotel> hotels;

    public Agence() {
        this.hotels = new ArrayList<>();
    }

    public Agence(String nom, String motDePasse) {
        this();
        this.nom = nom;
        this.motDePasse = motDePasse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }
    public void setHotel(Hotel hotel) {
        if (this.hotels == null) {
            this.hotels = new ArrayList<>();
        }
        this.hotels.add(hotel);
    }


    public void ajouterHotel(Hotel hotel) {
        hotels.add(hotel);
        hotel.setAgence(this);
    }
}
package Rest.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private int numChambre;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private int étage;
    private int nombreLit;
    private int prix;

    @Column(nullable = false)
    private boolean disponible;

    @OneToMany(mappedBy = "chambre")
    private List<Offre> offres;

    private String image;


    public Chambre() {}

    public Chambre(int numChambre, Hotel hotel, int étage, int nombreLit, int prix) {
        this.numChambre = numChambre;
        this.hotel = hotel;
        this.étage = étage;
        this.nombreLit = nombreLit;
        this.prix = prix;
    }

    public int getNumChambre() {
        return numChambre;
    }

    public void setNumChambre(int numChambre) {
        this.numChambre = numChambre;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public int getÉtage() {
        return étage;
    }

    public void setÉtage(int étage) {
        this.étage = étage;
    }

    public int getNombreLit() {
        return nombreLit;
    }

    public void setNombreLit(int nombreLit) {
        this.nombreLit = nombreLit;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Chambre{" +
                "numChambre=" + numChambre +
                ", hotel=" + hotel +
                ", étage=" + étage +
                ", nombreLit=" + nombreLit +
                ", prix=" + prix +
                ", disponible=" + disponible +
                ", image='" + image + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

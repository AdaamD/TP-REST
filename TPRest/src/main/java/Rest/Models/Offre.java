package Rest.Models;

import jakarta.persistence.*;

@Entity
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOffre;

    private String dateDisponibilite;
    private String dateExpiration;
    private int prix;

    // Nouveaux champs ajoutés
    private int numeroChambre;
    private int nombreLits;
    private String nomHotel;

    @ManyToOne
    @JoinColumn(name = "chambre_id")  // Assurez-vous de définir correctement le type de relation avec Chambre
    private Chambre chambre;


    public Offre() {
    }

    public Offre(String dateDisponibilite, String dateExpiration, int prix, int numeroChambre, int nombreLits, String nomHotel) {
        this.dateDisponibilite = dateDisponibilite;
        this.dateExpiration = dateExpiration;
        this.prix = prix;
        this.numeroChambre = numeroChambre;
        this.nombreLits = nombreLits;
        this.nomHotel = nomHotel;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public String getDateDisponibilite() {
        return dateDisponibilite;
    }

    public void setDateDisponibilite(String dateDisponibilite) {
        this.dateDisponibilite = dateDisponibilite;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    // Nouveaux getters et setters ajoutés
    public int getNumeroChambre() {
        return numeroChambre;
    }

    public void setNumeroChambre(int numeroChambre) {
        this.numeroChambre = numeroChambre;
    }

    public int getNombreLits() {
        return nombreLits;
    }

    public void setNombreLits(int nombreLits) {
        this.nombreLits = nombreLits;
    }

    public String getNomHotel() {
        return nomHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

}
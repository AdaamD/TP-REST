package Rest.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "client_id") // Assurez-vous de définir correctement le type de relation avec Client
    private Client client;

    private double prixAPayer;
    private String dateArrive;
    private String dateDepart;

    @ManyToOne
    @JoinColumn(name = "offre_id")
    private Offre offre;

    public Reservation() {
    }

    public Reservation(String nom, String prenom, Client client, String dateArrive, String dateDepart ,double prixAPayer, Offre offre) {
        this.nom = nom;
        this.prenom = prenom;
        this.client = client;
        this.dateArrive = dateArrive;
        this.dateDepart = dateDepart;
        this.prixAPayer = prixAPayer;
        this.offre = offre;
    }
    public Reservation(String nom, String prenom, Client client, double prixAPayer, Offre offre) {
        this.nom = nom;
        this.prenom = prenom;
        this.client = client;
        this.prixAPayer = prixAPayer;
        this.offre=offre ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getPrixAPayer() {
        return prixAPayer;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(String dateArrive) {
        this.dateArrive = dateArrive;
    }

    public Offre getOffre() {
        return offre;
    }
}

package Rest.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Client {

/* Attributs */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String mail;
    @Column(name = "telephone")
    private Integer telephone;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carte_credit_id", referencedColumnName = "id")
    private CarteCredit carteCredit;
    @JsonBackReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

/* Constructeur */

    public Client() {}
    public Client(String nom, String prenom, String mail, Integer telephone, CarteCredit carteCredit) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.telephone = telephone;
        this.carteCredit = carteCredit;
    }

/* Getters & Setters */

    public void ajouterReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setClient(this);
    }
    public Long getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getMail() {
        return mail;
    }
    public Integer getTelephone() {
        return telephone;
    }
    public CarteCredit getCarteCredit() {
        return carteCredit;
    }
    public List<Reservation> getReservations() {
        return reservations;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", telephone=" + telephone +
                ", carteCredit=" + carteCredit +
                ", reservations=" + reservations +
                '}';
    }

}

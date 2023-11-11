package Rest.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String mail;

    @Column(name = "telephone")  // Renommez la colonne pour éviter les conflits avec le mot clé SQL
    private Integer telephone;


    @Embedded
    private CarteCredit carteCredit;


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    public Client() {}

    public Client(String nom, String prenom, String mail, Integer telephone, CarteCredit carteCredit) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.telephone = telephone;
        this.carteCredit = carteCredit;
    }

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

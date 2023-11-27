package Rest.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class CarteCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private Date dateExp;
    private Integer crypto;

    public CarteCredit() {}

    public CarteCredit(Integer numero, Date dateExp, Integer crypto) {
        this.numero = numero;
        this.dateExp = dateExp;
        this.crypto = crypto;
    }

    // Getters and setters

    public Integer getNumero() {
        return numero;
    }

    public Date getDateExp() {
        return dateExp;
    }

    public Integer getCrypto() {
        return crypto;
    }
}

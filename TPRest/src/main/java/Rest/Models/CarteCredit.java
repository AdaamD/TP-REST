package Rest.Models;

import jakarta.persistence.*;


@Entity
public class CarteCredit {

/* Attributs */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numero;
    private String dateExp;
    private Integer crypto;

/* Constructeur */

    public CarteCredit() {}
    public CarteCredit(Integer numero, String dateExp, Integer crypto) {
        this.numero = numero;
        this.dateExp = dateExp;
        this.crypto = crypto;
    }

/* Getters & Setters */
    public Integer getNumero() {
        return numero;
    }
    public String getDateExp() {
        return dateExp;
    }
    public Integer getCrypto() {
        return crypto;
    }

}

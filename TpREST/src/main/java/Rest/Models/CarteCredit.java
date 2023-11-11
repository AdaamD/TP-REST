package Rest.Models;

import java.util.Date;

public class CarteCredit {

    private Integer numero;
    private Date dateExp;
    private Integer crypto;

    public CarteCredit() {}

    public CarteCredit(Integer numero, Date dateExp, Integer crypto) {
        this.numero = numero;
        this.dateExp = dateExp;
        this.crypto = crypto;
    }

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

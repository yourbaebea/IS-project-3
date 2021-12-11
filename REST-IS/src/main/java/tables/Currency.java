package tables;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "currencies")
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "currency")
    private String  currency;
    @Column(name = "exchange")
    private double  exchange;


    public Currency() {
    }

    public Currency(String currency, double  exchange) {
        this.currency = currency;
        this.exchange = exchange;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getExchange() {
        return exchange;
    }

    public void setExchange(double exchange) {
        this.exchange = exchange;
    }

    @Override
    public String toString() {
        return "Cliente [id = "+ id + "currency=" + currency + ", exchange=" + exchange +"]";
    }

}





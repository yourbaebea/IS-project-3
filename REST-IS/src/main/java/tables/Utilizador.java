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
@Table(name = "utilizador")
public class Utilizador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "name")
    private String  name;

    @Column(name = "tipo")
    private int  tipo;

    @Column(name = "managerid")
    private double  managerid;

    public Utilizador() {
    }

    public Utilizador(String name,int tipo, int  managerid) {
        this.name = name;
        this.tipo = tipo;
        this.managerid = managerid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getManagerid() {
        return managerid;
    }

    public void setManagerid(double managerid) {
        this.managerid = managerid;
    }

    @Override
    public String toString() {
        return "Cliente [id = "+ id + "Tipo=" + tipo + ", managerid=" + managerid +"]";
    }

}





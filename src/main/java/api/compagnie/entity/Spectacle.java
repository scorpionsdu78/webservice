package api.compagnie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Set;

@XmlRootElement
@Entity(name="spectacle")
public class Spectacle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idspectacle;
    private String nom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dates;
    private int nbplace;
    private int place_restante;
    private String prix;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "repertoire_idrepertoire")
    private Repertoire repertoire;

    @JsonIgnore
    @OneToMany(mappedBy = "spectacle",fetch = FetchType.LAZY)
    private Set<Reservation> reservations;

    public Spectacle() {
    }

    public Spectacle(String nom, Date dates, int nbplace, int place_restante, String prix, Repertoire repertoire) {
        this.nom = nom;
        this.dates = dates;
        this.nbplace = nbplace;
        this.place_restante = place_restante;
        this.prix = prix;
        this.repertoire = repertoire;
    }

    public int getIdspectacle() {
        return idspectacle;
    }

    public void setIdspectacle(int idspectacle) {
        this.idspectacle = idspectacle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public int getNbplace() {
        return nbplace;
    }

    public void setNbplace(int nbplace) {
        this.nbplace = nbplace;
    }

    public int getPlace_restante() {
        return place_restante;
    }

    public void setPlace_restante(int place_restante) {
        this.place_restante = place_restante;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public Repertoire getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(Repertoire repertoire) {
        this.repertoire = repertoire;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Spectacle{" +
                "idspectacle=" + idspectacle +
                ", nom='" + nom + '\'' +
                ", dates=" + dates +
                ", nbplace=" + nbplace +
                ", place_restante=" + place_restante +
                ", prix='" + prix + '\'' +
                ", repertoire=" + repertoire +
                '}';
    }
}

package api.compagnie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;



import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity(name="reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idresa;
    private String nom_reservation;
    private int nb_place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spectacle_idspectacle")
    private Spectacle spectacle;

    public Reservation() {
    }

    public Reservation(String nom_reservation, int nb_place, Spectacle spectacle) {
        this.nom_reservation = nom_reservation;
        this.nb_place = nb_place;
        this.spectacle = spectacle;
    }

    public int getIdresa() {
        return idresa;
    }

    public void setIdresa(int idresa) {
        this.idresa = idresa;
    }

    public String getNom_reservation() {
        return nom_reservation;
    }

    public void setNom_reservation(String nom_reservation) {
        this.nom_reservation = nom_reservation;
    }

    public int getNb_place() {
        return nb_place;
    }

    public void setNb_place(int nb_place) {
        this.nb_place = nb_place;
    }

    @JsonIgnore
    public Spectacle getSpectacle() {
        return spectacle;
    }

    public void setSpectacle(Spectacle spectacle) {
        this.spectacle = spectacle;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idresa=" + idresa +
                ", nom_reservation='" + nom_reservation + '\'' +
                ", nb_place=" + nb_place +
                ", spectacle=" + spectacle +
                '}';
    }
}

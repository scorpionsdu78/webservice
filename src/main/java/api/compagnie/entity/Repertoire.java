package api.compagnie.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
@Entity(name="repertoire")
public class Repertoire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idrepertoire;
    String nom;
    String auteur;
    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    Boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="repertoire_has_membre",joinColumns = {@JoinColumn(name="repertoire_idrepertoire")},inverseJoinColumns = {@JoinColumn(name="Membre_idMembre")})
    Set<Membre> membres;

    public Repertoire() {
    }

    public Repertoire(String nom, String auteur, Boolean active) {
        this.nom = nom;
        this.auteur = auteur;
        this.active = active;
        membres = new HashSet<Membre>();
    }

    public int getIdrepertoire() {
        return idrepertoire;
    }

    public void setIdrepertoire(int idrepertoire) {
        this.idrepertoire = idrepertoire;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Membre> getMembres() {
        return membres;
    }

    public void setMembres(Set<Membre> membres) {
        this.membres = membres;
    }

    @Override
    public String toString() {
        return "Repertoire{" +
                "idrepertoire=" + idrepertoire +
                ", nom='" + nom + '\'' +
                ", auteur='" + auteur + '\'' +
                ", active=" + active +
                '}';
    }
}

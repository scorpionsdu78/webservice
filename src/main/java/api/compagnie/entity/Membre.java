package api.compagnie.entity;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement
@Entity(name="membre")
public class Membre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idMembre;
    String nom;
    String prenom;
    String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="photo_membre",joinColumns = {@JoinColumn(name="membre_idMembre")},inverseJoinColumns = {@JoinColumn(name="Photo_idPhoto")})
    Set<Photo> photos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="membre_has_role",joinColumns = {@JoinColumn(name="Membre_idMembre")},inverseJoinColumns = {@JoinColumn(name="role_idrole")})
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Membre() {
    }

    public Membre(String nom, String prenom, String description) {
        this.nom = nom;
        this.prenom = prenom;
        this.description = description;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public int getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Membre{" +
                "idMembre=" + idMembre +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

package api.compagnie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement
@Entity(name="photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idphoto;
    String url;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="photo_article",joinColumns = {@JoinColumn(name="photo_idPhoto")},inverseJoinColumns = {@JoinColumn(name="article_idArticle")})
    Set<Article> article_idarticle;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="photo_membre",joinColumns = {@JoinColumn(name="photo_idPhoto")},inverseJoinColumns = {@JoinColumn(name="membre_idMembre")})
    Set<Membre> membreIdmembre;

    public Photo() {
    }

    public Photo(String url, Set<Article> article_idarticle, Set<Membre> membreIdmembre) {
        this.url = url;
        this.article_idarticle = article_idarticle;
        this.membreIdmembre = membreIdmembre;
    }

    public int getIdphoto() {
        return idphoto;
    }

    public void setIdphoto(int idphoto) {
        this.idphoto = idphoto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Article> getArticle_idarticle() {
        return article_idarticle;
    }

    public void setArticle_idarticle(Set<Article> article_idarticle) {
        this.article_idarticle = article_idarticle;
    }

    public Set<Membre> getMembreIdmembre() {
        return membreIdmembre;
    }

    public void setMembreIdmembre(Set<Membre> membreIdmembre) {
        this.membreIdmembre = membreIdmembre;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "idphoto=" + idphoto +
                ", url='" + url + '\'' +
                '}';
    }
}

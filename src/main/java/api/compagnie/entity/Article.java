package api.compagnie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
@Entity(name="article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idarticle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date publication;
    @Column(columnDefinition = "longtext")
    String content;
    String titre;
    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    Boolean featured;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date creation;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="photo_article",joinColumns = {@JoinColumn(name="article_idarticle")},inverseJoinColumns = {@JoinColumn(name="photo_idPhoto")})
    private Set<Photo> photos;

    public Article() {
        this.photos = new HashSet<>();
    }

    public Article(Date publication, String content, String titre, Boolean featured, Date creation) {
        this.publication = publication;
        this.content = content;
        this.titre = titre;
        this.featured = featured;
        this.creation = creation;
        this.photos = new HashSet<Photo>();
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public int getIdarticle() {
        return idarticle;
    }

    public void setIdarticle(int idarticle) {
        this.idarticle = idarticle;
    }

    public Date getPublication() {
        return publication;
    }

    public void setPublication(Date publication) {
        this.publication = publication;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean feature) {
        this.featured = feature;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    @Override
    public String toString() {
        return "Article{" +
                "idarticle=" + idarticle +
                ", publication=" + publication +
                ", content='" + content + '\'' +
                ", titre='" + titre + '\'' +
                ", feature=" + featured +
                ", creation=" + creation;
    }
}

package api.compagnie.entity;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@Entity(name="message")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idmessage;
    String emailenvoyeur;
    String message;
    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    Boolean traite;

    public Message(){

    }

    public Message(int id, String emailenvoyeur, String message, Boolean traite) {
        this.idmessage = id;
        this.emailenvoyeur = emailenvoyeur;
        this.message = message;
        this.traite = traite;
    }

    public Message(String emailenvoyeur, String message, Boolean traite) {
        this.emailenvoyeur = emailenvoyeur;
        this.message = message;
        this.traite = traite;
    }

    public int getIdmessage() {
        return idmessage;
    }

    public void setIdmessage(int idmessage) {
        this.idmessage = idmessage;
    }

    public String getEmailenvoyeur() {
        return emailenvoyeur;
    }

    public void setEmailenvoyeur(String emailenvoyeur) {
        this.emailenvoyeur = emailenvoyeur;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getTraite() {
        return traite;
    }

    public void setTraite(Boolean traite) {
        this.traite = traite;
    }

    @Override
    public String toString() {
        return "Message{" +
                "idmessage=" + idmessage +
                ", emailenvoyeur='" + emailenvoyeur + '\'' +
                ", message='" + message + '\'' +
                ", traite=" + traite +
                '}';
    }
}

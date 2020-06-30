package api.compagnie.controler;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.entity.Repertoire;
import api.compagnie.entity.Reservation;
import api.compagnie.entity.Spectacle;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class SpectacleControler {

    public List<Spectacle> getAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query<Spectacle> query = session.createQuery("select s from spectacle s",Spectacle.class);
        return query.getResultList();
    }

    public Spectacle getById(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return session.get(Spectacle.class,id);
    }

    public Spectacle insert(String nom, Date date, int nbPlace, int place_restante, String prix, Repertoire piece){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Spectacle spectacle = new Spectacle(nom,date,nbPlace,place_restante,prix,piece);
        session.save(spectacle);
        return spectacle;
    }

    public Spectacle update(int id, String nom, Date date, int nbPlace, int place_restante, String prix, Repertoire piece){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Spectacle spectacle = session.get(Spectacle.class,id);
        if(nom!=null && !nom.isEmpty())
            spectacle.setNom(nom);
        if(date !=null)
            spectacle.setDates(date);
        if(nbPlace >=0)
            spectacle.setNbplace(nbPlace);
        if (place_restante >=0 && place_restante <= spectacle.getNbplace())
            spectacle.setPlace_restante(place_restante);
        if(prix!=null && !prix.isEmpty())
            spectacle.setPrix(prix);
        if(piece !=null)
            spectacle.setRepertoire(piece);

        session.save(spectacle);
        return spectacle;
    }

    public Set<Reservation> delete(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Spectacle spectacle = session.get(Spectacle.class,id);
        Set<Reservation> reservations = spectacle.getReservations();
        session.delete(spectacle);
        return reservations;
    }

    public Spectacle reducePlace(int id, int nb){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Spectacle spectacle = session.get(Spectacle.class,id);
        spectacle.setPlace_restante(spectacle.getPlace_restante()-nb);
        return spectacle;
    }

}

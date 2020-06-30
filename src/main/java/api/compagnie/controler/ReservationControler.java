package api.compagnie.controler;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.entity.Reservation;
import api.compagnie.entity.Spectacle;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ReservationControler {

    public List<Reservation> getAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query<Reservation> query = session.createQuery("select r from reservation r",Reservation.class);
        return query.getResultList();
    }

    public Reservation getByID(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return session.get(Reservation.class,id);
    }

    public Reservation insert(String name, int nb_place, Spectacle spectacle){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Reservation reservation = null;
        if (nb_place <= spectacle.getPlace_restante()) {
             reservation = new Reservation(name, nb_place, spectacle);
            session.save(reservation);
        }
        return reservation;
    }

    public Reservation update(int id, String name, int nb_place){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Reservation reservation = session.get(Reservation.class,id);
        SpectacleControler spectacleControler = new SpectacleControler();
        System.out.println("nb place:" + nb_place);
        System.out.println("reservation place" + reservation.getNb_place());
        if(name!=null && !name.isEmpty()){
            reservation.setNom_reservation(name);
        }
        if (nb_place>0){
            System.out.println("entrez if");
            if(nb_place < reservation.getNb_place()){
                int dif = reservation.getNb_place() - nb_place;
                System.out.println(dif);
                spectacleControler.reducePlace(reservation.getSpectacle().getIdspectacle(),-dif);
            }else if(nb_place > reservation.getNb_place()){
                System.out.println("entrez if sup");
                int dif = nb_place - reservation.getNb_place();
                System.out.println(dif);
                spectacleControler.reducePlace(reservation.getSpectacle().getIdspectacle(),dif);
            }
            reservation.setNb_place(nb_place);
        }
        session.save(reservation);
        return reservation;
    }

    public Reservation delete(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Reservation reservation = session.get(Reservation.class,id);
        session.delete(reservation);
        return reservation;
    }

    public void deleteByReference(Reservation reservation){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.delete(reservation);
    }
}

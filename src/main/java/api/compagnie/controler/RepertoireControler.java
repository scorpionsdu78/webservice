package api.compagnie.controler;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.entity.Repertoire;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RepertoireControler {

    public List<Repertoire> getAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query<Repertoire> query = session.createQuery("select r from repertoire r",Repertoire.class);
        return query.getResultList();
    }

    public Repertoire getById(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return session.get(Repertoire.class,id);
    }

    public Repertoire insert(String nom, String auteur, Boolean active){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Repertoire repertoire = new Repertoire(nom,auteur,active);
        session.save(repertoire);
        return repertoire;
    }

    public void Delete(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Repertoire repertoire = session.get(Repertoire.class,id);
        session.delete(repertoire);
    }

    public Repertoire update(int id, String nom, String auteur, Boolean active){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Repertoire repertoire = session.get(Repertoire.class,id);
        if(nom!=null && nom.isEmpty())
            repertoire.setNom(nom);
        if(auteur!= null && auteur.isEmpty())
            repertoire.setAuteur(auteur);
        repertoire.setActive(active);
        session.save(repertoire);
        return repertoire;
    }
}

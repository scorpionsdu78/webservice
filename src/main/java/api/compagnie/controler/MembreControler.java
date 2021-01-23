package api.compagnie.controler;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.entity.Membre;
import api.compagnie.entity.Photo;
import api.compagnie.entity.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

public class MembreControler {

    public List<Membre> getAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query<Membre> query = session.createQuery("select m from membre m",Membre.class);
        return query.getResultList();
    }

    public Membre getByID(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return session.get(Membre.class,id);
    }

    public Membre insert(String nom, String prenom, String description){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Membre membre = new Membre(nom,prenom,description);
        session.save(membre);
        return membre;
    }

    public Membre update(int id, String nom, String prenom, String description, Set<Photo> photos, Set<Role> roles){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Membre membre = session.get(Membre.class,id);
        System.out.println(description);
        if(nom!=null && !nom.isEmpty()) {
            System.out.println("entrez dans le if1");
            membre.setNom(nom);
        }
        if(prenom!=null && !prenom.isEmpty()) {
            System.out.println("entrez dans le if2");
            membre.setPrenom(prenom);
        }
        if(description!=null && !description.isEmpty()) {
            System.out.println("entrez dans le if3");
            membre.setDescription(description);
        }
        if(photos!=null)
            membre.setPhotos(photos);
        if(roles!=null)
            membre.setRoles(roles);
        session.save(membre);
        return membre;
    }

    public Set<Photo> delete(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Membre membre = session.get(Membre.class,id);
        Set<Photo> photos = membre.getPhotos();
        session.delete(membre);
        return photos;
    }
}

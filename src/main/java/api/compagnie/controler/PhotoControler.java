package api.compagnie.controler;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.entity.Article;
import api.compagnie.entity.Membre;
import api.compagnie.entity.Photo;
import org.hibernate.Session;

import java.util.Set;

public class PhotoControler {

    public Photo getByID(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        //Photo photo = session.get(Photo.class,id);
        //System.out.println(photo);
        return session.get(Photo.class,id);
    }

    public Photo insertPhoto(String url, Set<Article> article, Set<Membre> membre){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Photo photo = new Photo(url,article,membre);
        session.save(photo);
        return photo;
    }

    public Photo EditPhoto(int id, String url){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Photo photo = session.get(Photo.class,id);
        if(url != null && !url.isEmpty()){
            photo.setUrl(url);
        }
        /*if (idArticle > 0){
            photo.setArticle_idarticle(session.get(Article.class,idArticle));
        }
        if(idMembre > 0){
            photo.setMembreIdmembre(session.get(Membre.class,idMembre));
        }*/
        session.save(photo);
        return photo;
    }

    public void RemovArticle(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Photo photo = session.get(Photo.class,id);
        photo.setArticle_idarticle(null);
        session.persist(photo);
    }

    public void RemovMembre(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Photo photo = session.get(Photo.class,id);
        photo.setMembreIdmembre(null);
        session.persist(photo);
    }

    public void Delete(int id){
        Session session =HibernateUtil.getSessionFactory().getCurrentSession();
        Photo photo = session.get(Photo.class,id);
        session.delete(photo);
    }
}

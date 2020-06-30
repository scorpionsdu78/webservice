package api.compagnie.controler;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.entity.Article;
import api.compagnie.entity.Photo;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class ArticleControler {

    public List<Article> getAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query<Article> articleQuery = session.createQuery("select a from article a", Article.class);
        return articleQuery.getResultList();
    }

    public Article getByID(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return session.get(Article.class,id);
    }

    public Set<Photo> Delete(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Article article = session.get(Article.class,id);
        Set<Photo> set = article.getPhotos();
        session.delete(article);
        return set;
    }

    public Article Update(int id, Date publi, String content, String titre, Boolean featured){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Article article = session.get(Article.class,id);
        article.setPublication(publi);
        if(content!=null && !content.isEmpty() )
            article.setContent(content);
        if(titre!=null && !titre.isEmpty())
            article.setTitre(titre);
        if(featured!=null && featured != article.getFeatured())
            article.setFeatured(featured);
        session.save(article);
        return article;
    }

    public Article insert(Date publi, String content, String titre, Boolean featured){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Date date = new Date();
        Article article = new Article(publi,content,titre,featured,date);
        session.save(article);
        return article;
    }
}

package api.compagnie.restful;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.controler.ArticleControler;
import api.compagnie.controler.PhotoControler;
import api.compagnie.entity.Article;
import api.compagnie.entity.Photo;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("/Article")
public class ArticleService {
    ArticleControler controler;

    public ArticleService() {
        this.controler = new ArticleControler();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        Session session = null;
        List<Article> article = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            article = controler.getAll();
            tx.commit();
        }catch (Exception e){
            //sortie exception
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null){
                session.close();
            }
        }

        //sortie article non trouvé => erreur 404
        if(article == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        //sortie correcte!
        return Response.status(Response.Status.OK).entity(article).build();
    }

    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getById(@PathParam("id") int id){

        Session session = null;
        Article article = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            article = controler.getByID(id);
            tx.commit();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null){
                session.close();
            }
        }

        if(article == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(article).build();
    }

    @Path("/{id}")
    @DELETE
    public void Delete(@PathParam("id") int id){

        Session session = null;
        Article article = null;
        Transaction tx = null;

        PhotoControler controlerPhoto = new PhotoControler();

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            Set<Photo> photos = controler.Delete(id);
            for (Photo p: photos) {
                p.setArticle_idarticle(null);
                session.persist(p);
            }
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session != null){
                session.close();
            }
        }

    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Update(@PathParam("id") int id, Article article){

        Session session = null;
        Article articlemod = null;
        Transaction tx = null;

        System.out.println(id);
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            articlemod = controler.Update(id,article.getPublication(),article.getContent(),article.getTitre(),article.getFeatured(),article.getPhotos());
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null){
                session.close();
            }
        }

        if(articlemod == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(articlemod).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertWithPhoto(Article article){
        Session session = null;
        Article article1 = null;
        Transaction tx = null;
        PhotoControler photoControler = new PhotoControler();


        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            article1 = controler.insert(article.getPublication(),article.getContent(),article.getTitre(),article.getFeatured());
            if(article1 != null){
                for (Photo p : article.getPhotos()) {
                    article1.getPhotos().add(session.get(Photo.class,p.getIdphoto()));
                }
            }
            tx.commit();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null){
                session.close();
            }
        }

        if(article1 == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(article1).build();

    }

}

package api.compagnie.restful;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.controler.PhotoControler;
import api.compagnie.entity.Article;
import api.compagnie.entity.Membre;
import api.compagnie.entity.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mariadb.jdbc.internal.common.packet.OKPacket;

import javax.ws.rs.core.Response;
import java.util.Scanner;

public class PhotoService {
    PhotoControler controler;

    public PhotoService(){
        controler = new PhotoControler();
    }

    public Response getByid(){
        System.out.println("what id: ");
        Scanner sc = new Scanner(System.in);
        int id=sc.nextInt();

        Session session = null;
        Photo photo = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            photo = controler.getByID(id);
            if(photo.getArticle_idarticle() != null)
                Hibernate.initialize(photo.getArticle_idarticle());
            if(photo.getMembreIdmembre() != null)
                Hibernate.initialize(photo.getMembreIdmembre());
            tx.commit();
        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null){
                session.close();
            }
        }

        if(photo == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(photo).build();
    }

    public Response newPhoto(){
        Article article = null;
        Membre membre = null;
        Session session = null;
        Transaction tx = null;
        Photo photo = null;


        System.out.println("URL: ");
        Scanner sc = new Scanner(System.in);
        String url = sc.nextLine();



        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();


            //photo = controler.insertPhoto(url,article,membre);


            tx.commit();

        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null){
                session.close();
            }
        }

        if(photo == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(photo).build();
    }

    public Response editPhoto(){
        Session session = null;
        Transaction tx = null;
        Photo photo = null;

        Scanner sc = new Scanner(System.in);


        System.out.println("photo: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("URL: ");
        String url = sc.nextLine();

        System.out.println("article: ");
        int articleID = sc.nextInt();
        sc.nextLine();


        System.out.println("Membre: ");
        int idMembre = sc.nextInt();
        sc.nextLine();

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            photo = controler.EditPhoto(id,url);
            if(photo.getArticle_idarticle() != null)
                Hibernate.initialize(photo.getArticle_idarticle());
            if(photo.getMembreIdmembre() != null)
                Hibernate.initialize(photo.getMembreIdmembre());
            tx.commit();

        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null){
                session.close();
            }
        }

        if(photo == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(photo).build();

    }

    public void Delete(){
        Session session = null;
        Transaction tx = null;

        Scanner sc = new Scanner(System.in);


        System.out.println("photo: ");
        int id = sc.nextInt();
        sc.nextLine();

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            controler.Delete(id);
            tx.commit();

        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }finally {
            if(session != null){
                session.close();
            }
        }

    }
}

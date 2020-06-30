package api.compagnie.restful;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.controler.RepertoireControler;
import api.compagnie.entity.Membre;
import api.compagnie.entity.Repertoire;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Path("/Repertoire")
public class RepertoireService {
    RepertoireControler controler;

    public RepertoireService(){
        controler = new RepertoireControler();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {


        List<Repertoire> repertoire = null;
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            repertoire = controler.getAll();
            tx.commit();

        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } finally {
            if (session != null)
                session.close();
        }

        if(repertoire == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(repertoire).build();
    }

    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getById(@PathParam("id") int id) {

        Repertoire repertoire = null;
        Session session = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            repertoire = controler.getById(id);
            tx.commit();

        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session!=null)
                session.close();
        }

        if(repertoire == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(repertoire).build();
    }


    public Repertoire getByIdEager() {
        Scanner sc = new Scanner(System.in);

        System.out.println("id? ");
        int id = sc.nextInt();

        Repertoire repertoire = null;
        Session session = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            repertoire = controler.getById(id);
            Hibernate.initialize(repertoire.getMembres());
            tx.commit();

        }catch (Exception e){
            if(tx != null)
                tx.rollback();
        }finally {
            if(session!=null)
                session.close();
        }

        return repertoire;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(Repertoire repertoire){

        Repertoire repertoire1 = null;
        Session session = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            repertoire1 = controler.insert(repertoire.getNom(),repertoire.getAuteur(),repertoire.getActive());

            for (Membre m : repertoire.getMembres()) {
                repertoire1.getMembres().add(session.get(Membre.class,m.getIdMembre()));
            }

            tx.commit();

        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }finally {
            if(session!=null)
                session.close();
        }

        if(repertoire1 == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(repertoire1).build();

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id,Repertoire repertoire){

        Repertoire repertoire1 = null;
        Session session = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            repertoire1 = controler.update(id,repertoire.getNom(),repertoire.getAuteur(),repertoire.getActive());

            Set<Membre> membreSet = new HashSet<Membre>();

            for (Membre m : repertoire.getMembres()) {
                membreSet.add(session.get(Membre.class,m.getIdMembre()));
            }

            repertoire1.setMembres(membreSet);

            tx.commit();

        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }finally {
            if(session!=null)
                session.close();
        }

        if(repertoire1 == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(repertoire1).build();

    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") int id) {


        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            controler.Delete(id);
            tx.commit();

        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
        } finally {
            if (session != null)
                session.close();
        }

    }

}

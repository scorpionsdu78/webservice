package api.compagnie.restful;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.controler.ReservationControler;
import api.compagnie.controler.SpectacleControler;
import api.compagnie.entity.Repertoire;
import api.compagnie.entity.Reservation;
import api.compagnie.entity.Spectacle;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.util.ToolBox;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Path("/Spectacle")
public class SpectacleService {
    SpectacleControler controler;

    public SpectacleService() {
        controler = new SpectacleControler();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        Session session = null;
        Transaction tx = null;
        List<Spectacle> spectacle = null;



        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            spectacle = controler.getAll();

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }finally {
            if(session !=null){
                session.close();
            }
        }

        if(spectacle == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(spectacle).build();

    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByID(@PathParam("id") int id){
        Session session = null;
        Transaction tx = null;
        Spectacle spectacle = null;



        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            spectacle = controler.getById(id);

            tx.commit();
        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session !=null){
                session.close();
            }
        }

        if(spectacle == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(spectacle).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(Spectacle spectacle){
        Session session = null;
        Transaction tx = null;
        Spectacle spectacle1 = null;


        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            Repertoire repertoire = session.get(Repertoire.class,spectacle.getRepertoire().getIdrepertoire());

            repertoire.setActive(Boolean.TRUE);

            session.save(repertoire);

            spectacle1 = controler.insert(spectacle.getNom(),spectacle.getDates(),spectacle.getNbplace(),spectacle.getNbplace(),spectacle.getPrix(),repertoire);


            tx.commit();
        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session !=null){
                session.close();
            }
        }

        if(spectacle1 == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(spectacle1).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") int id){
        Session session = null;
        Transaction tx = null;

        Scanner scanner = new Scanner(System.in);

        ReservationControler reservationControler = new ReservationControler();


        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            Set<Reservation> reservationSet = controler.delete(id);

            /*for (Reservation r: reservationSet) {
                reservationControler.deleteByReference(r);
            }*/

            tx.commit();
        }catch (Exception e){
            if(tx!= null){
                tx.rollback();
            }
        }finally {
            if(session != null)
                session.close();
        }

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Spectacle spectacle){
        Session session = null;
        Transaction tx = null;
        Spectacle spectacle1 = null;


        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            Repertoire repertoire = session.get(Repertoire.class,spectacle.getRepertoire().getIdrepertoire());

            spectacle1 = controler.update(id,spectacle.getNom(),spectacle.getDates(),spectacle.getNbplace(),spectacle.getPlace_restante(),spectacle.getPrix(),repertoire);


            tx.commit();
        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session !=null){
                session.close();
            }
        }

        if(spectacle1 == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(spectacle1).build();
    }
}

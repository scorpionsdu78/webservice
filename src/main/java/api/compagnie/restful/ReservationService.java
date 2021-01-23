package api.compagnie.restful;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.controler.ReservationControler;
import api.compagnie.controler.SpectacleControler;
import api.compagnie.entity.Reservation;
import api.compagnie.entity.Spectacle;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Scanner;

@Path("/Reservation")
public class ReservationService {
    ReservationControler controler;

    public ReservationService(){
        controler = new ReservationControler();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){

        List<Reservation> reservation = null;
        Session session = null;
        Transaction tx = null;


        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            reservation = controler.getAll();


            tx.commit();

        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }finally {
            if(session != null)
                session.close();
        }

        if(reservation == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(reservation).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id){

        Reservation reservation = null;
        Session session = null;
        Transaction tx = null;


        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            reservation = controler.getByID(id);

            //Hibernate.unproxy(reservation.getSpectacle());

            tx.commit();

        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }finally {
            if(session != null)
                session.close();
        }


        if(reservation == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(reservation).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(Reservation reservation){
        Reservation reservation1 = null;
        Session session = null;
        Transaction tx = null;


        SpectacleControler spectacleControler = new SpectacleControler();

        System.out.println(reservation.getNb_place());

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            int id = reservation.getSpectacle().getIdspectacle();

            Spectacle spectacle = session.get(Spectacle.class,id);

            reservation1 = controler.insert(reservation.getNom_reservation(),reservation.getNb_place(),spectacle);

            spectacleControler.reducePlace(reservation1.getSpectacle().getIdspectacle(),reservation1.getNb_place());

            tx.commit();
        }catch (Exception e){
            if(tx!=null){
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null)
                session.close();
        }

        if(reservation1 == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(reservation1).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UPDATE(Reservation reservation,@PathParam("id") int id){
        Reservation reservation1 = null;
        Session session = null;
        Transaction tx = null;


        System.out.println(reservation.getNb_place());

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            reservation1 = controler.update(id,reservation.getNom_reservation(),reservation.getNb_place());


            tx.commit();
        }catch (Exception e){
            if(tx!=null){
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }finally {
            if(session != null)
                session.close();
        }

        if(reservation1 == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(reservation1).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void Delete(@PathParam("id") int id){

        Session session = null;
        Transaction tx = null;

        SpectacleControler spectacleControler = new SpectacleControler();


        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();


            Reservation spectacle = controler.delete(id);

            System.out.println(spectacle);

            spectacleControler.reducePlace(spectacle.getSpectacle().getIdspectacle(),-spectacle.getNb_place());


            tx.commit();

        }catch (Exception e){
            e.printStackTrace();
            if(tx != null)
                tx.rollback();

        }finally {
            if(session != null)
                session.close();
        }
    }

}

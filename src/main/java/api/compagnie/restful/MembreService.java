package api.compagnie.restful;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.controler.MembreControler;
import api.compagnie.controler.PhotoControler;
import api.compagnie.entity.Membre;
import api.compagnie.entity.Photo;
import api.compagnie.entity.Role;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Path("Membre")
public class MembreService {
    MembreControler controler;

    public MembreService() {
        controler = new MembreControler();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        Session session = null;
        List<Membre> membre = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            membre = controler.getAll();
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

        if(membre == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(membre).build();
    }

    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getById(@PathParam("id") int id){
        Session session = null;
        Membre membre = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            membre = controler.getByID(id);
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

        if(membre == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(membre).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertComplete(Membre membre){

        Session session = null;
        Membre membre1 = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            membre1 = controler.insert(membre.getNom(),membre.getPrenom(),membre.getDescription());
            membre1.setRoles(new HashSet<Role>());


            for (Role r : membre.getRoles()) {
                membre1.getRoles().add(session.get(Role.class,r.getIdrole()));
            }

            membre1.setPhotos(new HashSet<Photo>());

            for (Photo p : membre.getPhotos()) {
                membre1.getPhotos().add(session.get(Photo.class,p.getIdphoto()));
            }

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

        if(membre1 == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(membre1).build();
    }

    @Path("/{id}")
    @DELETE
    public void deleteCascade(@PathParam("id") int id){

        Session session = null;
        Membre membre = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            Set<Photo> photos = controler.delete(id);
            for (Photo p: photos) {
                p.setMembreIdmembre(null);
            }
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

    @Path("/{id}/role")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRole(@PathParam("id")int id,List<Role> roles){

        System.out.println("entre dans le put");

        Session session = null;
        Membre membre = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            membre = controler.getByID(id);
            Set<Role> set = new HashSet<>();
            System.out.println(roles.size());
            for (Role r : roles){
                System.out.println(r.getNom());
                set.add(session.get(Role.class,r.getIdrole()));
            }

            membre.setRoles(set);
            session.save(membre);

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

        if(membre == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(membre).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") int id, Membre membre){
        Session session = null;
        Membre membre1 = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            membre1 = controler.update(id,membre.getNom(),membre.getPrenom(),membre.getDescription());

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

        if(membre1 == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(membre1).build();
    }
}

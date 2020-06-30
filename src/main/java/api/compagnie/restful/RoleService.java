package api.compagnie.restful;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.controler.MembreControler;
import api.compagnie.controler.RoleControler;
import api.compagnie.entity.Membre;
import api.compagnie.entity.Role;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Scanner;

@Path("/Role")
public class RoleService {

    private RoleControler controler;

    public RoleService() {
        controler = new RoleControler();
    }

    public Role getByIdWithMember(){
        System.out.println("what ID: ");
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();

        Session session = null;
        Transaction tx = null;
        Role role = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            role = controler.getByID(id);
            Hibernate.initialize(role.getMembreSet());

            tx.commit();

        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            if(session != null){
                session.close();
            }
        }
        return role;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){

        Session session = null;
        Transaction tx = null;
        List<Role> role = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            role = controler.getAll();

            tx.commit();

        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } finally {
            if(session != null){
                session.close();
            }
        }

        if(role == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(role).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id){

        Session session = null;
        Transaction tx = null;
        Role role = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            role = controler.getByID(id);

            tx.commit();

        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } finally {
            if(session != null){
                session.close();
            }
        }

        if(role == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(role).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Insert(Role role){


        Session session = null;
        Transaction tx = null;
        Role role1 = null;


        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            role1 = controler.insert(role.getNom());

            tx.commit();

        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } finally {
            if(session != null){
                session.close();
            }
        }

        if(role1 == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(role1).build();
    }

    @Path("/{id}")
    @DELETE
    public void delete(@PathParam("id") int id){
        Session session = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            controler.Delete(id);


            tx.commit();

        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }

        } finally {
            if(session != null){
                session.close();
            }
        }

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id,Role role){


        Session session = null;
        Transaction tx = null;
        Role role1 = null;


        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            role1 = controler.update(id,role.getNom());

            tx.commit();

        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } finally {
            if(session != null){
                session.close();
            }
        }

        if(role1 == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(role1).build();
    }

}

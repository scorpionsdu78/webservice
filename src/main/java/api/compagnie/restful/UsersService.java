package api.compagnie.restful;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.controler.UsersControler;
import api.compagnie.entity.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/User")
public class UsersService {

    private UsersControler controler;

    public UsersService() {
        controler = new UsersControler();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}")
    public Response checkPassword(@PathParam("username") String user){
        Session session = null;
        Transaction tx = null;
        Users u;


        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            u = controler.getUserByName(user);
            System.out.println("user get:" + u);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null){
                session.close();
            }
        }

        if(u == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(u).build();
    }

    /*@Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByID(@PathParam("id") int id){
        Session session = null;
        Transaction tx = null;
        Users users = null;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            users = controler.getByid(id);

        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null)
                session.close();
        }

        if(users == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(users).build();
    }*/

    /*
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(Users users){
        Session session = null;
        Transaction tx =null;
        Users user = null;


        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            user = controler.createUser(users);
            tx.commit();
        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null)
                session.close();
        }

        if(user == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(user).build();
    }*/

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Update(@PathParam("id") int id,Users users){
        Session session = null;
        Transaction tx =null;
        Users users1 = null;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            users1 = controler.ChangePassword(id,users.getPassword());
            tx.commit();

        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null)
                session.close();
        }

        if(users1 == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(users1).build();
    }

    @Path("/{id}")
    @DELETE
    public void delete(@PathParam("id") int id){
        Session session = null;
        Transaction tx =null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            controler.Delete(id);
            tx.commit();

        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }finally {
            if(session != null)
                session.close();
        }
    }


}

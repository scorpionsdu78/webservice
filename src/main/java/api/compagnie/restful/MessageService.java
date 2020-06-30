package api.compagnie.restful;

import api.compagnie.connection.*;
import api.compagnie.controler.MessageControler;
import api.compagnie.entity.Message;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Scanner;

@Path("/Message")
public class MessageService {

    MessageControler controler;

    public MessageService() {
        controler = new MessageControler();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessageID(@PathParam("id") int id){

        Session session = null;
        Message message = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            message = controler.getMessage(id);
            tx.commit();
        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null){
                session.close();
            }
        }

        if(message == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(message).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessages(){
        Session session = null;
        List<Message> messages = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            messages = controler.getMessages();
            tx.commit();
        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null){
                session.close();
            }
        }

        if(messages == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(messages).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMessage(Message message) {

        Session session = null;
        Transaction tx = null;

        Message newMessage = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            newMessage = controler.insertMessage(message);
            tx.commit();
        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null){
                session.close();
            }
        }

        if(newMessage == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(newMessage).build();
    }

    @Path("/{id}")
    @DELETE
    public void supprMessage(@PathParam("id") int id) {

        Session session = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            controler.DeleteMessage(id);
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public void edditMessage(){
        Session session = null;
        Message message = null;
        Transaction tx = null;

        message = new Message(2,"francois.boni@efrei.net","test",Boolean.FALSE);

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            message = controler.EditMessage(message);
            tx.commit();
        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            e.printStackTrace();
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response treated(@PathParam("id") int id){
        Session session = null;
        Transaction tx = null;
        Message message = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx=session.beginTransaction();
            message = controler.treated(id);
            tx.commit();
        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }finally {
            if(session != null){
                session.close();
            }
        }

        if(message == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(message).build();
    }

}

package api.compagnie.controler;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.entity.Message;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class MessageControler {

    public List<Message> getMessages(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query<Message> query = session.createQuery("select m from message m",Message.class);
        List<Message> messages = query.getResultList();
        return messages;
    }

    public Message getMessage(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return session.get(Message.class,id);
    }

    public Message insertMessage(Message message){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.persist(message);
        return message;
    }

    public void DeleteMessage(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Message message = session.get(Message.class,id);
        session.delete(message);
    }

    public Message EditMessage(Message message){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return (Message)session.merge(message);
    }

    public Message treated(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Message message = getMessage(id);
        message.setTraite(Boolean.TRUE);
        session.persist(message);
        return message;
    }

}

package api.compagnie.controler;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.entity.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UsersControler {


    public Users getByid(int id){

        Session session = null;
        Users users = null;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            users = session.get(Users.class,id);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session != null)
                session.close();
        }

        return users;
    }

    public List<Users> getUsers(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query<Users> query = session.createQuery("select u from users u", Users.class);
        List<Users> users = query.getResultList();
        return users;
    }

    public Users createUser(Users users){
        Session session = null;
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.save(users);
        return users;

    }

    public Users ChangePassword(int id, String password){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Users users = session.get(Users.class,id);
        users.setPassword(password);
        session.save(users);
        return users;
    }

    public void Delete(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Users users = session.get(Users.class,id);
        session.delete(users);
    }

}

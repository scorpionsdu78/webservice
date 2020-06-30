package api.compagnie.controler;

import api.compagnie.connection.HibernateUtil;
import api.compagnie.entity.Membre;
import api.compagnie.entity.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RoleControler {

    public List<Role> getAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query<Role> roleQuery = session.createQuery("select r from role r",Role.class);
        return roleQuery.getResultList();
    }

    public Role getByID(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return session.get(Role.class,id);
    }

    public Role insert(String nom){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Role role = new Role(nom);
        session.save(role);
        return role;
    }

    public void addRole(int idmembre, Role role){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.get(Membre.class,idmembre).getRoles().add(role);
    }

    public Role update(int id, String nom){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Role role = session.get(Role.class,id);
        if(nom!=null && nom.isEmpty())
            role.setNom(nom);
        session.save(role);
        return role;
    }

    public void Delete(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Role role = session.get(Role.class,id);
        session.delete(role);
    }
}

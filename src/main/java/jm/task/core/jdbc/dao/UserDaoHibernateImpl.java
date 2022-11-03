package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;
    private final SessionFactory factory;

    public UserDaoHibernateImpl() {
        factory = Util.getFactory();
    }


    @Override
    public void createUsersTable() {
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE if NOT EXISTS `users` (\n" +
                    "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(45) NOT NULL,\n" +
                    "  `lastName` varchar(45) NOT NULL,\n" +
                    "  `age` int NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE if EXISTS users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            User user = new User(name, lastName, age);
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            System.out.println("User " + name + " добавлен в базу данных ");
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            List<User> userList = session.createQuery("FROM User", User.class).getResultList();
            return userList;
        } finally {
            session.close();
        }

    }

    @Override
    public void cleanUsersTable() {
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
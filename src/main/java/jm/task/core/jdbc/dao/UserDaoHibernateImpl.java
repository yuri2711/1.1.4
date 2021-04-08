package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.transaction.internal.TransactionImpl;
import org.hibernate.query.NativeQuery;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.Queue;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE user (" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "username VARCHAR(100) NOT NULL, " +
                    "userfirst_name VARCHAR(100) NOT NULL, " +
                    "userage INT NOT NULL, " +
                    "PRIMARY KEY(id));").executeUpdate();
        } catch (Throwable e) {
            System.out.println("Таблица уже существует");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE user").executeUpdate();
        } catch (Throwable e) {
            System.out.println("Таблица не может быть удалена");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.createSQLQuery("INSERT INTO user (username, userfirst_name, userage) values ('" + name + "', '" + lastName + "', " + age + ")")
                    .executeUpdate();
        } catch (Throwable e) {
            System.out.println("Error added");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM user WHERE id = " + id).executeUpdate();
        } catch (Throwable throwable) {
            System.out.println("Удалить юзера не получилось");
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session s = Util.getSession();
//        User u = s.get(User.class, 2L);//Получение объекта по идентификатору
//
//        System.out.println(u.getName() + " " + u.getAge() + " test best");

        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        Query query1 = s.createQuery(query);
        List<User> list = query1.getResultList();
        s.close();

        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createSQLQuery("DELETE FROM user").executeUpdate();
        session.close();
    }
}

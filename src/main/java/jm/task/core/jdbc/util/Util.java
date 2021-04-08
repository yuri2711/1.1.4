package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/sakila?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String pass = "7777";


    public static Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(url, user, pass);
        return connection;
    }


    private static Connection connection;
    private static SessionFactory session;

    static {
        try {
            Configuration configuration = new Configuration().addAnnotatedClass(User.class)
                    .setProperty("hibernate.connection.url", "jdbc:MySQL://localhost:3306/sakila")
                    .setProperty("hibernate.connection.user", "root")
                    .setProperty("hibernate.connection.password", "7777")
                    .setProperty("hibernate.connection.charasterEncoding", "utf-8")
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .setProperty("hibernate.format_sql", "true");
                    //.setProperty("hibernate.hbm2ddl.auto", "update");

            session = configuration.buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Session getSession() throws HibernateException {
        return session.openSession();
    }

}

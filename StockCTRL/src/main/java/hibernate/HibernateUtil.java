package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try{
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }catch (Throwable e){
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Session getSession(){
        return sessionFactory.openSession();
    }
    public static void shutdown(){
        sessionFactory.close();
    }

}

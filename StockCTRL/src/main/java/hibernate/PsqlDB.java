package hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PsqlDB {
    private static Session session;
    public static List<String> sendQuery(String queryName) {


         session = HibernateUtil.getSession();
        List<String> queryResult = new ArrayList<>();

        try{
//            Query<DataBaseUsers> query = session.createQuery("FROM DataBaseUsers", DataBaseUsers.class);
//            List<DataBaseUsers> users = query.getResultList();
            Query<String> query = session.createQuery(queryName, String.class);
            queryResult = query.getResultList();


        }catch(Exception e){
            e.printStackTrace();
        }finally {
            //session.close();
           // HibernateUtil.shutdown();
        }
        return queryResult;

    }

    public static  void closeHibernateSession(){
        session.close();
    }
}
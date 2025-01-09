package hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PsqlDB {
    private static Session session;
    public static void main(String[] args){
        List<Integer> data = sendQuery("SELECT id FROM DataBaseProducts", Integer.class);

        List<String> arr = new ArrayList<>();
        arr.add(data.get(1).toString());
        System.out.println(arr);
        System.out.println(sendQuery("SELECT id FROM DataBaseProducts", String.class));
        System.out.println(sendQuery("SELECT name FROM DataBaseProducts", String.class));
    }


    public static <T> List<T> sendQuery(String queryName, Class<T> type) {


         session = HibernateUtil.getSession();
        List<T> queryResult = new ArrayList<>();

        try{
//            Query<DataBaseUsers> query = session.createQuery("FROM DataBaseUsers", DataBaseUsers.class);
//            List<DataBaseUsers> users = query.getResultList();
            Query<T> query = session.createQuery(queryName, type);
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
package hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.dialect.AbstractPostgreSQLJsonPGObjectType;
import org.hibernate.dialect.PostgreSQLJsonPGObjectJsonType;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public static void insert( int userId, String status, JSONArray products){
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();


            System.out.println(userId + " " +status+ " " +products);
            // Wykonujemy natywne zapytanie SQL
            session.createNativeQuery("INSERT INTO orders ( user_id, status, products) VALUES ('" + userId +"','"+status + "','" + products + "')")
                    .executeUpdate();

            transaction.commit();
            System.out.println("Successfull added new order");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public static void updateCartList(String queryName){
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            Query nativeQuery = session.createNativeQuery(queryName);
            nativeQuery.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public static List<Object[]> sendNativeQuery(String queryName) {


        session = HibernateUtil.getSession();
        List<Object[]> queryResult = new ArrayList<>();

        try{
            Query<Object[]> query = session.createNativeQuery(queryName);
            queryResult = query.getResultList();


        }catch(Exception e){
            e.printStackTrace();
        }
        return queryResult;

    }

    public static <T> List<T> sendQuery(String queryName, Class<T> type) {


         session = HibernateUtil.getSession();
        List<T> queryResult = new ArrayList<>();

        try{
            Query<T> query = session.createQuery(queryName, type);
            queryResult = query.getResultList();


        }catch(Exception e){
            e.printStackTrace();
        }
        return queryResult;

    }

    public static  void closeHibernateSession(){
        session.close();
    }
    public static Session getSession() {
        return session;
    }
}
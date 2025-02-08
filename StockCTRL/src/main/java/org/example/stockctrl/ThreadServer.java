package org.example.stockctrl;

import hibernate.DataBaseOrders;
import hibernate.PsqlDB;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ThreadServer {
    private final Socket socket;

    public ThreadServer(Socket socket) {
        this.socket = socket;
    }


    public void run(Socket clientSocket) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            boolean ok = true;
            System.out.println("-- Server connected with client --" + clientSocket.getInetAddress());
            while (ok) {

                String data = br.readLine();
                JSONObject json = new JSONObject(data);
                System.out.println("I order a data: " + json.toString());
                if (Objects.equals(json.optString("operation"), "giveProductsList")) {
                    inquiryProductList(bw);
                }
                if (Objects.equals(json.optString("operation"), "giveCartList")) {
                    inquiryCartList(bw);
                }
                if (Objects.equals(json.optString("operation"), "addProductToCart")) {
                    updateCartList( json.optString("product"));
                }
                if (Objects.equals(json.optString("operation"), "deleteProductFromCart")) {
                    JSONObject bufor = new JSONObject(json.optString("productId"));
                    deleteProductFromCartList(bw, (int)  bufor.get("id"));
                }
                if (Objects.equals(json.optString("operation"), "buy")) {
                    updateOrderList( json.optString("order"), bw);
                }
                if (Objects.equals(json.optString("operation"), "deleteCartList")) {
                    deleteCartList(bw);
                }
                if (Objects.equals(json.optString("operation"), "giveOrdersList")) {
                    inquiryOrdersList(bw);
                }
                if (Objects.equals(json.optString("operation"), "checkLogin")) {
                    checkLogin(bw, json);
                }
            }
            socket.close();
        } catch (SocketException e) {
            System.out.println("-- Client disconnected --");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void checkLogin(BufferedWriter bw, JSONObject orderedJson){

        String login = orderedJson.optString("login");
        String password = orderedJson.optString("password");


        List<String> queryLogin = PsqlDB.sendQuery("SELECT password FROM DataBaseUsers WHERE login = '" +login + "'", String.class);


        JSONObject toSend = new JSONObject();
        toSend.put("toSend", false);
        if(queryLogin.isEmpty()){
            toSend.put("answerLogin", false);
        }else{
            //System.out.println(queryLogin.get(0));
            //System.out.println(password);
            if (Objects.equals(queryLogin.get(0), password)){
                toSend.put("answerLogin", true);
            }else{
                toSend.put("answerLogin", false);
            }
        }



        System.out.println("Send to client: " + toSend);
        try {
            bw.write(toSend.toString());
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    private static void inquiryOrdersList(BufferedWriter bw){

        //List<String> query = PsqlDB.sendQuery("DataBaseUsers", "WHERE userName = (SELECT userName from DataBaseUsers WHERE userName ='Filip')");
        List<DataBaseOrders> query = PsqlDB.sendQuery("SELECT o FROM DataBaseOrders o WHERE o.userId = 1", DataBaseOrders.class);

        JSONArray orderListJson = new JSONArray();


        for(int i =0; i< query.size(); i++){

            JSONObject item = new JSONObject();
            item.put("id", query.get(i).getId());
            item.put("status", query.get(i).getStatus());

            item.put("products", query.get(i).getProducts());

            orderListJson.put(item);
        }
        System.out.println("Data from database: "+ orderListJson);

        JSONObject toSend = new JSONObject();


        toSend.put("toSend", false);
        toSend.put("ordersList", orderListJson);
        System.out.println("Send to client: " + toSend);
        try {
            bw.write(toSend.toString());
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    private static void deleteCartList(BufferedWriter bw){

        JSONArray emptyCartList = new JSONArray();



        PsqlDB.updateCartList("UPDATE users SET cartlist = '" + emptyCartList+ "' WHERE id = 1");

        JSONObject toSend = new JSONObject();


        toSend.put("toSend", false);
        // System.out.println("Send to client: " + toSend);
        try {
            bw.write(toSend.toString());
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private static void updateOrderList( String order, BufferedWriter bw){


        JSONObject newOrder = new JSONObject(order);

        System.out.println("New order" +  newOrder);


        PsqlDB.addOrder(newOrder.optInt("userId"),newOrder.optString("status"),newOrder.optJSONArray("orderList"));

        JSONObject toSend = new JSONObject();

        toSend.put("toSend", false);
        // System.out.println("Send to client: " + toSend);
        try {
            bw.write(toSend.toString());
            bw.newLine();
            bw.flush();
            System.out.println("Server sended");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    private static void deleteProductFromCartList(BufferedWriter bw, int id){
        //System.out.println(id);

        List<String> actualCartList = PsqlDB.sendQuery("SELECT cartList FROM DataBaseUsers WHERE id = 1", String.class);

        JSONArray oldCartList = new JSONArray(actualCartList.get(0));


        for(int i =0; i < oldCartList.length(); i++){
            JSONObject currentItem = oldCartList.getJSONObject(i);
            JSONObject currentProduct = currentItem.getJSONObject("product");
            //System.out.println(currentProduct.optString("id"));
            //System.out.println(id);
            //System.out.println(Objects.equals(currentProduct.optString("id"), id+""));

            if(Objects.equals(currentProduct.optString("id"), id+"")){
                //System.out.println("Before " + oldCartList);

                if((int) currentItem.get("count") > 1){
                    currentItem.put("count", (int) currentItem.get("count") - 1);
                    System.out.println("Decrease product count");
                }else{
                    oldCartList.remove(i);
                    System.out.println("Remove product");
                }

                //System.out.println("After " + oldCartList);
                //System.out.println("Increase product count");


                break;
            }
        }

        PsqlDB.updateCartList("UPDATE users SET cartlist = '" + oldCartList+ "' WHERE id = 1");

        JSONObject toSend = new JSONObject();


        toSend.put("toSend", false);
       // System.out.println("Send to client: " + toSend);
        try {
            bw.write(toSend.toString());
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void updateCartList( String product){

        List<String> actualCartList = PsqlDB.sendQuery("SELECT cartList FROM DataBaseUsers WHERE id = 1", String.class);

        JSONArray oldCartList = new JSONArray(actualCartList.get(0));
        //System.out.println("Old " + oldCartList);

        JSONObject newProduct = new JSONObject(product);
        Boolean incrementCount = false;

        for(int i =0; i < oldCartList.length(); i++){
            JSONObject currentItem = oldCartList.getJSONObject(i);
            JSONObject currentProduct = currentItem.getJSONObject("product");

            if(Objects.equals(currentProduct.optString("id"), newProduct.optString("id"))){

                incrementCount=true;
                currentItem.put("count", (int) currentItem.get("count") + 1);
                System.out.println("Increase product count");


                break;
            }
        }

        if(!incrementCount){
            JSONObject newProductToCart = new JSONObject();
            newProductToCart.put("count", 1);
            newProductToCart.put("product", newProduct);
            oldCartList.put(newProductToCart);
            //System.out.println("Finish " + oldCartList);
            System.out.println("Add product to cart");

        }

        PsqlDB.updateCartList("UPDATE users SET cartlist = '" + oldCartList+ "' WHERE id = 1");

    }

    private static void inquiryCartList(BufferedWriter bw){

        //List<String> query = PsqlDB.sendQuery("DataBaseUsers", "WHERE userName = (SELECT userName from DataBaseUsers WHERE userName ='Filip')");
        List<String> queryCartList = PsqlDB.sendQuery("SELECT cartList FROM DataBaseUsers WHERE id = 1", String.class);


//        for(int i =0; i< queryJsonCartList.size(); i++){
////            JSONObject item = new JSONObject();
////            item.put("id", queryJsonCartList.get(i));
////            item.put("name", queryProductName.get(i));
////            item.put("price", queryProductPrice.get(i));
////            productListJson.put(item);
//        }
        System.out.println("Data from database: "+ queryCartList.get(0));

        JSONObject toSend = new JSONObject();


        toSend.put("toSend", false);
        toSend.put("cartList", queryCartList.get(0));
        System.out.println("Send to client: " + toSend);
        try {
            bw.write(toSend.toString());
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static void inquiryProductList(BufferedWriter bw){

        //List<String> query = PsqlDB.sendQuery("DataBaseUsers", "WHERE userName = (SELECT userName from DataBaseUsers WHERE userName ='Filip')");
        List<Integer> queryProductId = PsqlDB.sendQuery("SELECT id FROM DataBaseProducts", Integer.class);
        List<String> queryProductName = PsqlDB.sendQuery("SELECT name FROM DataBaseProducts", String.class);
        List<Integer> queryProductPrice = PsqlDB.sendQuery("SELECT price FROM DataBaseProducts", Integer.class);

        JSONArray productListJson = new JSONArray();


        for(int i =0; i< queryProductName.size(); i++){
            JSONObject item = new JSONObject();
            item.put("id", queryProductId.get(i));
            item.put("name", queryProductName.get(i));
            item.put("price", queryProductPrice.get(i));
            productListJson.put(item);
        }
        System.out.println("Data from database: "+ productListJson);

        JSONObject toSend = new JSONObject();


        toSend.put("toSend", false);
        toSend.put("productsList", productListJson);
        System.out.println("Send to client: " + toSend);
        try {
            bw.write(toSend.toString());
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}

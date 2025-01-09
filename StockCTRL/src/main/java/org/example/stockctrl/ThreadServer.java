package org.example.stockctrl;

import hibernate.PsqlDB;
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


//                String dane = br.readLine();
//                JSONObject json = new JSONObject(dane);
//                System.out.println("I order an info: "+ json.optInt("Number"));
//                JSONObject odp = new JSONObject();
//                Boolean answer = false;
//                if(json.optInt("Number") == 4){
//                    answer = true;
//                }
//                odp.put("Odp", answer);
//                bw.write(odp.toString());
//                bw.newLine();
//                bw.flush();
            }
            socket.close();
        } catch (SocketException e) {
            System.out.println("-- Client disconnected --");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void inquiryProductList(BufferedWriter bw){

        //List<String> query = PsqlDB.sendQuery("DataBaseUsers", "WHERE userName = (SELECT userName from DataBaseUsers WHERE userName ='Filip')");
        List<Integer> queryId = PsqlDB.sendQuery("SELECT id FROM DataBaseProducts", Integer.class);
        List<String> queryName = PsqlDB.sendQuery("SELECT name FROM DataBaseProducts", String.class);

        JSONObject query = new JSONObject();

        for(int i =0; i< queryName.size(); i++){
            //System.out.println("Wsadzam " + queryId.get(i).toString() +" " + queryName.get(i));
            query.put(queryId.get(i).toString(), queryName.get(i));
        }
        System.out.println("Data from database: "+ query);

        JSONObject toSend = new JSONObject();

        toSend.put("toSend", false);
        toSend.put("productsList", query.toString());
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

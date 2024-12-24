package org.example.stockctrl;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ThreadServer {
    private Socket socket;

    public ThreadServer(Socket socket) {
        this.socket = socket;
    }


    public void run( Socket clientSocket) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            boolean ok =true;
            System.out.println("-- Server connected with client --" + clientSocket.getInetAddress() );
            while(ok) {

                    String data = br.readLine();
                    JSONObject json = new JSONObject(data);
                    System.out.println("I order a data: " + json.toString());

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
}

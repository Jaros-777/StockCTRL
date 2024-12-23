package org.example.stockctrl;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class Thread {
    private Socket socket;

    public Thread(Socket socket) {
        this.socket = socket;
    }


    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println("Server in work");
            boolean ok = false;
            while(!ok) {
                String dane = br.readLine();
                JSONObject json = new JSONObject(dane);
                System.out.println("I order an info: "+ json.optInt("Number"));
                JSONObject odp = new JSONObject();
                Boolean answer = false;
                if(json.optInt("Number") == 4){
                    answer = true;
                }
                odp.put("Odp", answer);
                bw.write(odp.toString());
                bw.newLine();
                bw.flush();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

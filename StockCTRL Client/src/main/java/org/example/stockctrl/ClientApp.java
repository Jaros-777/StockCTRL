package org.example.stockctrl;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("StockCTRL Client");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        //launch();

        try {
            Socket socket = new Socket("localhost", 8080);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);
            boolean ok = false;
            while(!ok) {
                System.out.println("Podaj liczbe ");
                int n = scanner.nextInt();
                JSONObject json = new JSONObject();
                json.put("Number", n);
                bw.write(json.toString());
                bw.newLine();
                bw.flush();
                String odp = br.readLine();
                JSONObject odpJson = new JSONObject(odp);
                System.out.println(odpJson.optBoolean("Odp"));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
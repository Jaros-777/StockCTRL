package org.example.stockctrl;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.json.JSONObject;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;


public class ClientApp extends Application {


    private static Socket socket;
    private static boolean isClientRunning = true;
    private static JSONObject infoJson = new JSONObject();
    //private static JSONObject controllerInfo = new JSONObject();

    public static void controllerToClient(JSONObject json) {
        infoJson = json;
    }

    public static JSONObject ClientToController(){

        return infoJson;
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon-warehouse.png")));
        stage.getIcons().add(image);
        //System.out.println(getClass().getResource("/styling.css"));
        scene.getStylesheets().add(getClass().getResource("/styling.css").toExternalForm());

        stage.setTitle("StockCTRL Client");
        stage.setScene(scene);

        stage.show();

        stage.setOnCloseRequest(event -> {
            isClientRunning = false;
            System.out.println("-- Client close --");

        });

        new Thread(ClientApp::startClient).start();
    }


    public static void startClient() {
        System.out.println("-- Client is started --");
        try {
            Socket socket = new Socket("localhost", 8080);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //Scanner scanner = new Scanner(System.in);

            System.out.println("-- Client " + socket.getInetAddress() + " connected with server --");


            while (isClientRunning) {
                if (infoJson.optBoolean("toSend")) {
                    if (Objects.equals(infoJson.optString("operation"), "changeUserDetails")) {
                        sendToServerChangeUserDetails(bw);
                    }
                    if (Objects.equals(infoJson.optString("operation"), "buy")) {
                        sendToServerBuy(bw);
                    }
                    if (Objects.equals(infoJson.optString("operation"), "giveProductsList")) {
                        sendToServerInquiryProductsList(bw, br);
                    }
                    if (Objects.equals(infoJson.optString("operation"), "giveCartList")) {
                        sendToServerInquiryCartList(bw, br);
                    }
                    if (Objects.equals(infoJson.optString("operation"), "addProductToCart")) {
                        sendToServerUpdateCart(bw);
                    }
                }

//                System.out.println("Podaj liczbe ");
//                int n = scanner.nextInt();
//                JSONObject json = new JSONObject();
//                json.put("Number", n);
//                bw.write(json.toString());
//                bw.newLine();
//                bw.flush();
//                String odp = br.readLine();
//                JSONObject odpJson = new JSONObject(odp);
//                System.out.println(odpJson.optBoolean("Odp"));

            }

        } catch (ConnectException e) {
            System.out.println("-- Cannon connect with server --");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendToServerUpdateCart(BufferedWriter bw) {


        try {
            bw.write(infoJson.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        infoJson.put("toSend", false);
    }
    public static void sendToServerBuy(BufferedWriter bw) {

        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("operation", infoJson.optString("operation"));
        jsonToSend.put("id", infoJson.optJSONArray("id"));

        System.out.println("Client send data to server: " + jsonToSend);


        try {
            bw.write(jsonToSend.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        infoJson.put("toSend", false);
    }

    public static void sendToServerChangeUserDetails(BufferedWriter bw) {

        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("operation", infoJson.optString("operation"));
        jsonToSend.put("userDetails", infoJson.optJSONObject("userDetails"));

        System.out.println("Client send data to server: " + jsonToSend);

        try {
            bw.write(jsonToSend.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        infoJson.put("toSend", false);
    }


    public static void sendToServerInquiryCartList(BufferedWriter bw, BufferedReader br) {

//        JSONObject jsonToSend = new JSONObject();
//        jsonToSend.put("operation", infoJson.optString("operation"));

        System.out.println("Client send data to server: " + infoJson);

        try {
            bw.write(infoJson.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean run = true;
        while(run){


            try {
//                    System.out.println("I waiting to order a data from server");
                String data = br.readLine();
                JSONObject answer = new JSONObject(data);

                if(Objects.equals(answer.optString("toSend"), "false")){
                    infoJson = new JSONObject(data);
//                        System.out.println("I order a data from server: "+ infoJson);

                    run = false;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


//        infoJson.put("toSend", false);
    }

    public static void sendToServerInquiryProductsList(BufferedWriter bw, BufferedReader br) {

//        JSONObject jsonToSend = new JSONObject();
//        jsonToSend.put("operation", infoJson.optString("operation"));

        System.out.println("Client send data to server: " + infoJson);

        try {
            bw.write(infoJson.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean run = true;
        while(run){


                try {
//                    System.out.println("I waiting to order a data from server");
                    String data = br.readLine();
                    JSONObject answer = new JSONObject(data);

                    if(Objects.equals(answer.optString("toSend"), "false")){
                        infoJson = new JSONObject(data);
//                        System.out.println("I order a data from server: "+ infoJson);

                        run = false;
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

        }


//        infoJson.put("toSend", false);
    }



    public static void main(String[] args) {
        infoJson.put("toSend", false);
        launch();

    }
}
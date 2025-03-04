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


    private static boolean isClientRunning = true;
    private static JSONObject infoJson = new JSONObject();

    public static void controllerToClient(JSONObject json) {
        infoJson = json;
    }

    public static JSONObject ClientToController(){

        return infoJson;
    }

    private static int userID;
    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userId) {
        userID = userId;
    }




    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon-warehouse.png")));
        stage.getIcons().add(image);
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

            System.out.println("-- Client " + socket.getInetAddress() + " connected with server --");


            while (isClientRunning) {
                if (infoJson.optBoolean("toSend")) {
                    if (Objects.equals(infoJson.optString("operation"), "changeUserDetails")) {
                        sendToServerChangeUserDetails(bw);
                    }
                    if (Objects.equals(infoJson.optString("operation"), "giveUserDetails")) {
                        giveUserDetails(bw, br);
                    }
                    if (Objects.equals(infoJson.optString("operation"), "buy")) {
                        sendToServerBuy(bw, br);
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
                    if (Objects.equals(infoJson.optString("operation"), "deleteProductFromCart")) {
                        deleteItemFromCart(bw, br);
                    }
                    if (Objects.equals(infoJson.optString("operation"), "deleteCartList")) {
                        deleteCartList(bw, br);
                    }
                    if (Objects.equals(infoJson.optString("operation"), "giveOrdersList")) {
                        sendToServerInquiryOrdersList(bw, br);
                    }
                    if (Objects.equals(infoJson.optString("operation"), "checkLogin")) {
                        checkLogin(bw, br);
                    }
                    if (Objects.equals(infoJson.optString("operation"), "registerUser")) {
                        registerUser(bw, br);
                    }
                }


            }

        } catch (ConnectException e) {
            System.out.println("-- Cannon connect with server --");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void registerUser(BufferedWriter bw, BufferedReader br) {


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
                String data = br.readLine();
                JSONObject answer = new JSONObject(data);

                if(Objects.equals(answer.optString("toSend"), "false")){
                    infoJson = new JSONObject(data);
                    run = false;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }
    public static void checkLogin(BufferedWriter bw, BufferedReader br) {


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
                String data = br.readLine();
                JSONObject answer = new JSONObject(data);

                if(Objects.equals(answer.optString("toSend"), "false")){
                    infoJson = new JSONObject(data);
                    run = false;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }
    public static void sendToServerInquiryOrdersList(BufferedWriter bw, BufferedReader br) {


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
                String data = br.readLine();
                JSONObject answer = new JSONObject(data);

                if(Objects.equals(answer.optString("toSend"), "false")){
                    infoJson = new JSONObject(data);

                    run = false;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }
    public static void deleteCartList(BufferedWriter bw, BufferedReader br) {


        try {
            bw.write(infoJson.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(true){


            try {
                String data = br.readLine();
                JSONObject answer = new JSONObject(data);

                if(Objects.equals(answer.optString("toSend"), "false")){
                    infoJson = new JSONObject(data);

                    break;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
    public static void deleteItemFromCart(BufferedWriter bw, BufferedReader br) {


        try {
            bw.write(infoJson.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(true){


            try {
                String data = br.readLine();
                JSONObject answer = new JSONObject(data);

                if(Objects.equals(answer.optString("toSend"), "false")){
                    infoJson = new JSONObject(data);

                    break;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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
    public static void sendToServerBuy(BufferedWriter bw, BufferedReader br) {

        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("operation", infoJson.optString("operation"));
        jsonToSend.put("order", infoJson.optString("order"));

        System.out.println("Client send data to server: " + jsonToSend);


        try {
            bw.write(jsonToSend.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        while(true){


            try {
//                    System.out.println("I waiting to order a data from server");
                String data = br.readLine();
                JSONObject answer = new JSONObject(data);

                if(Objects.equals(answer.optString("toSend"), "false")){
                    infoJson = new JSONObject(data);
                        //System.out.println("I order a data from server: "+ infoJson);
                    System.out.println("Client order");
                    break;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


        //infoJson.put("toSend", false);
    }

    public static void giveUserDetails(BufferedWriter bw, BufferedReader br) {


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
                String data = br.readLine();
                JSONObject answer = new JSONObject(data);

                if(Objects.equals(answer.optString("toSend"), "false")){
                    infoJson = new JSONObject(data);
                    run = false;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }

    public static void sendToServerChangeUserDetails(BufferedWriter bw) {

        System.out.println("Client send data to server: " + infoJson);

        try {
            bw.write(infoJson.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        infoJson.put("toSend", false);
    }

    public static void sendToServerInquiryCartList(BufferedWriter bw, BufferedReader br) {


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
                String data = br.readLine();
                JSONObject answer = new JSONObject(data);

                if(Objects.equals(answer.optString("toSend"), "false")){
                    infoJson = new JSONObject(data);

                    run = false;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }

    public static void sendToServerInquiryProductsList(BufferedWriter bw, BufferedReader br) {


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
                    String data = br.readLine();
                    JSONObject answer = new JSONObject(data);

                    if(Objects.equals(answer.optString("toSend"), "false")){
                        infoJson = new JSONObject(data);

                        run = false;
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

        }


    }



    public static void main(String[] args) {
        infoJson.put("toSend", false);
        launch();

    }
}
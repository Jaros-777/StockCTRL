package org.example.stockctrl;

import hibernate.HibernateUtil;
import hibernate.PsqlDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

public class ServerApp extends Application {


    private static boolean isServerRunning = true;
    private static ServerSocket serverSocket;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerApp.class.getResource("server-main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon-warehouse.png")));
        stage.getIcons().add(image);
        stage.setTitle("StockCTRL Server");
        stage.setScene(scene);
        //stage.show();

        stage.setOnCloseRequest(event -> {
            isServerRunning = false;
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        new Thread(() -> {
            try {
                startServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();


    }

    private static void startServer() throws IOException {
        System.out.println("-- Server is started --");
        try {
            serverSocket = new ServerSocket(8080);
            while (isServerRunning) {
                Socket socket = serverSocket.accept();
                ThreadServer thread = new ThreadServer(socket);
                thread.run(socket);
            }
            //serverSocket.close();
        } catch (SocketException e) {
            if (!isServerRunning && serverSocket.isClosed()) {
                if (PsqlDB.getSession() != null) {

                    PsqlDB.closeHibernateSession();
                }
                HibernateUtil.shutdown();
                System.out.println("-- Server close --");

            } else {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch();

    }


}
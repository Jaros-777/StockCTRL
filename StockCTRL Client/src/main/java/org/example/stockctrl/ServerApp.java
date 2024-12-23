package org.example.stockctrl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerApp.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("StockCTRL Server");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        //launch();

        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while(true) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(socket);
                thread.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
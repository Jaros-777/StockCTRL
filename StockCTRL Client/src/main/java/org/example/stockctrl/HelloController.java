package org.example.stockctrl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private Label welcomeText;


    @FXML
    public void switchSceneToMainViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(main.load());
        Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    public void switchSceneToStartViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(HelloApplication.class.getResource("start-view.fxml"));
        Scene scene = new Scene(main.load());
        Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    public void switchSceneToProductViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(HelloApplication.class.getResource("products-view.fxml"));
        Scene scene = new Scene(main.load());
        Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
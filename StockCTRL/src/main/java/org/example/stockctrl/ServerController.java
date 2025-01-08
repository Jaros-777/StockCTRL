package org.example.stockctrl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerController {
    @FXML
    public void switchSceneToServerMainViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("server-main-view.fxml"));
        Scene scene = new Scene(main.load());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void switchSceneToServerOrdersViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("server-orders-view.fxml"));
        Scene scene = new Scene(main.load());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void switchSceneToServerStockLevelViev(ActionEvent event) throws IOException {

        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("server-stock-level-view.fxml"));
        Scene scene = new Scene(main.load());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }
}

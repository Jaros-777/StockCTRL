package org.example.stockctrl;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class ClientController {

    @FXML
    private TextField userName;
    @FXML
    private TextField userSurname;
    @FXML
    private TextField userAddress;
    @FXML
    private static ListView<String> productsList = new ListView<>();



    @FXML
    public void switchSceneToMainViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(main.load());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void switchSceneToStartViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("start-view.fxml"));
        Scene scene = new Scene(main.load());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void switchSceneToProductViev(ActionEvent event) throws IOException {

        //inquiryProductsList();
        new Thread(ClientController::inquiryProductsList).start();

        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("products-view.fxml"));
        Scene scene = new Scene(main.load());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);


    }

    @FXML
    public void switchSceneToOrderViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("order-view.fxml"));
        Scene scene = new Scene(main.load());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void switchSceneToSettingsViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("settings-view.fxml"));
        Scene scene = new Scene(main.load());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    //SERVER
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

    //LOGIC
    @FXML
    public void buyProducts(ActionEvent event) {
        JSONObject json = new JSONObject();
        json.put("toSend", true);
        json.put("operation", "buy");
        JSONArray arr = new JSONArray();
        arr.put(1);
        arr.put(2);
        arr.put(3);
        arr.put(4);
        json.put("id", arr);

        ClientApp.controllerToClient(json);

    }

    @FXML
    public void changeUserDetails(ActionEvent e) {
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("toSend", true);
        jsonToSend.put("operation", "changeUserDetails");

        JSONObject userDetails = new JSONObject();
        userDetails.put("id", 2);
        userDetails.put("name", userName.getText());
        userDetails.put("surname", userSurname.getText());
        userDetails.put("address", userAddress.getText());

        jsonToSend.put("userDetails", userDetails);

        //System.out.println("Controller test: "+ jsonToSend);

        ClientApp.controllerToClient(jsonToSend);
    }


    private static void inquiryProductsList() {
        System.out.println("Start downloading products list from server");
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("toSend", true);
        jsonToSend.put("operation", "giveProductsList");
        ClientApp.controllerToClient(jsonToSend);


         String orderedProductList= "";




            while (true) {
                if (Objects.equals(ClientApp.ClientToController().optString("toSend"), "false")) {
                    orderedProductList = ClientApp.ClientToController().optString("productsList");
                    System.out.println("Received product list: " + orderedProductList);
                    break;
                }
            }


            if(orderedProductList != null && !orderedProductList.isEmpty()){
                String cleanedString = orderedProductList.replace("[", "").replace("]", ""); // Usuń nawiasy
                String[] productsListArray = cleanedString.split(",\\s*"); // Podziel po przecinku i opcjonalnych spacjach
                productsList.getItems().clear();
                productsList.getItems().addAll(productsListArray);
            }else{
                System.out.println("List is empty");
            }
            System.out.println("Finished downloading products from server");







//            String[] food = {"cos","cos"};
//            productsList.getItems().addAll(food);

    }

}
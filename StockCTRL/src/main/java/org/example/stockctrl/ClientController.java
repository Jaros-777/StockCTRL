package org.example.stockctrl;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class ClientController implements Initializable {

    @FXML
    private TextField userName;
    @FXML
    private TextField userSurname;
    @FXML
    private TextField userAddress;
    @FXML
    private Button cartBtn = new Button("Cart (0)");
    @FXML
    private ListView<Product> productsList = new ListView<>();
    private final ObservableList<Product> productsListData = FXCollections.observableArrayList();
    private List<String> cartList = new ArrayList<>();


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
        FXMLLoader waitingLoader = new FXMLLoader(ClientApp.class.getResource("waiting-view.fxml"));
        Scene waitingScene = new Scene(waitingLoader.load());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(waitingScene);


        CompletableFuture.runAsync(() -> {
            inquiryProductsList();


            Platform.runLater(() -> {
                try {
                    FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("products-view.fxml"));
                    Scene scene = new Scene(main.load());
                    //stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    ClientController controller = main.getController();
                    controller.updateProductsList(productsListData); // Make sure to pass the data stage.setScene(scene);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

    }

    public void updateProductsList(ObservableList<Product> products) {
        productsListData.clear();
        productsListData.addAll(products);
        productsList.setItems(productsListData);
        //System.out.println("Updated ListView with productListData: " + productsList.getItems());
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


    public class Product extends HBox{
        Label label = new Label();
        Button button = new Button("Add to cart");

        private String index;
        Product(String productName, String index){
            super();
            this.index = index;

            label.setText(productName);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("clicked " + index);
                    cartList.add(index);
                    cartBtn.setText("Cart " +"(" + cartList.size() + ")");
                }
            });

            this.getChildren().addAll(label,spacer, button);
        }


    }

    private void inquiryProductsList() {
        System.out.println("Start downloading products list from server");
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("toSend", true);
        jsonToSend.put("operation", "giveProductsList");
        ClientApp.controllerToClient(jsonToSend);


        String orderedProductList = "";


        while (true) {
            if (Objects.equals(ClientApp.ClientToController().optString("toSend"), "false")) {
                orderedProductList = ClientApp.ClientToController().optString("productsList");
                System.out.println("Received product list: " + orderedProductList);
                break;
            }
        }


        if (orderedProductList != null && !orderedProductList.isEmpty()) {

            JSONObject orderedProdJson = new JSONObject(orderedProductList);

            productsListData.clear();

            for(int i = 1; i<orderedProdJson.length(); i++ ){

               productsListData.add(new Product(orderedProdJson.optString(""+i), ""+i)) ;

            }

            productsList.setItems(productsListData);

        } else {
            System.out.println("List is empty");
        }
        System.out.println("Finished downloading products from server");


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productsList.setItems(productsListData);

    }
}
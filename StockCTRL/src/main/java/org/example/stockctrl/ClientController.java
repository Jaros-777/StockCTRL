package org.example.stockctrl;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;


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
    private ListView<ProductFX> productsList = new ListView<>();
    private final ObservableList<ProductFX> productsListData = FXCollections.observableArrayList();
    @FXML
    private ListView<ProductCartFX> cartList = new ListView<>();
    private final ObservableList<ProductCartFX> cartListData = FXCollections.observableArrayList();


    @FXML
    public void switchSceneToMainViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(main.load());
        scene.getStylesheets().add(getClass().getResource("/styling.css").toExternalForm());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void switchSceneToStartViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("start-view.fxml"));
        Scene scene = new Scene(main.load());
        scene.getStylesheets().add(getClass().getResource("/styling.css").toExternalForm());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void switchSceneToProductViev(ActionEvent event) throws IOException {
        FXMLLoader waitingLoader = new FXMLLoader(ClientApp.class.getResource("waiting-view.fxml"));
        Scene waitingScene = new Scene(waitingLoader.load());
        waitingScene.getStylesheets().add(getClass().getResource("/styling.css").toExternalForm());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(waitingScene);


        CompletableFuture.runAsync(() -> {
            inquiryProductsList();


            Platform.runLater(() -> {
                try {
                    FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("products-view.fxml"));
                    Scene scene = new Scene(main.load());
                    scene.getStylesheets().add(getClass().getResource("/styling.css").toExternalForm());
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

    public void updateProductsList(ObservableList<ProductFX> products) {
        productsListData.clear();
        productsListData.addAll(products);
        productsList.setItems(productsListData);
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

    @FXML
    public void switchSceneToCartViev(ActionEvent event) throws IOException {
        FXMLLoader waitingLoader = new FXMLLoader(ClientApp.class.getResource("waiting-view.fxml"));
        Scene waitingScene = new Scene(waitingLoader.load());
        waitingScene.getStylesheets().add(getClass().getResource("/styling.css").toExternalForm());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(waitingScene);


        CompletableFuture.runAsync(() -> {
            inquiryCartList();


            Platform.runLater(() -> {
                try {
                    FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("cart-view.fxml"));
                    Scene scene = new Scene(main.load());
                    scene.getStylesheets().add(getClass().getResource("/styling.css").toExternalForm());
                    //stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    ClientController controller = main.getController();
                    controller.updateCartList(cartListData); // Make sure to pass the data stage.setScene(scene);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });


    }

    public void updateCartList(ObservableList<ProductCartFX> products) {
        cartListData.clear();
        cartListData.addAll(products);
        cartList.setItems(cartListData);
        System.out.println("Updated ListView with cartlist: " + cartList.getItems());
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


    public class ProductFX extends HBox {
        Label productName = new Label();
        Label productPrice = new Label();
        Button button = new Button("Add to cart");
        Product item;
        private final int productId;
        private int count=0;

        ProductFX(Product product) {
            super();
            item = product;
            this.productId = product.getId();

            productName.setText(product.getName());
            BigDecimal price = BigDecimal.valueOf(product.getPrice() * 0.24).setScale(2, RoundingMode.HALF_UP);
            productPrice.setText(price +"$");
            Region spacer1 = new Region();
            Region spacer2 = new Region();
            HBox.setHgrow(spacer1, Priority.ALWAYS);
            HBox.setHgrow(spacer2, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Start adding product to cart");

                    //{"product":{"price":2999,"name":"Iphone 13 PRO MAX","id":1},"count":1}
                    JSONObject product = new JSONObject();
                    product.put("id", item.getId());
                    product.put("name", item.getName());
                    product.put("price", item.getPrice());



                    JSONObject jsonToSend = new JSONObject();
                    jsonToSend.put("toSend", true);
                    jsonToSend.put("operation", "addProductToCart");
                    jsonToSend.put("product", product.toString());
                    //System.out.println(jsonToSend);
                    //System.out.println("przed");
                    ClientApp.controllerToClient(jsonToSend);
                    //System.out.println("po");

                }

            });

            this.getChildren().addAll(productName, spacer1,productPrice,spacer2,button);
        }

        public int getProductId(){
            return productId;
        }
        public void setCount(int count){
            this.count = count;
        }
        public int getCount(){
            return count;
        }

        public String toString(){
            return item + " count: " + count;
        }

    }

    public static class ProductCartFX extends HBox {
        Label productName = new Label();
        Label productPrice = new Label();
        Button button = new Button("Delete");
        Label productCount = new Label();
        Product item;
        private final int productId;
        private int count=0;

        ProductCartFX(Product product,int count) {
            super();
            item = product;
            this.productId = product.getId();
            this.count = count;

            productName.setText(product.getName());
            BigDecimal price = BigDecimal.valueOf(product.getPrice() * 0.24).setScale(2, RoundingMode.HALF_UP);
            productPrice.setText(price +"$");
            productCount.setText(String.valueOf(count));
            Region spacer1 = new Region();
            Region spacer2 = new Region();
            Region spacer3 = new Region();
            HBox.setHgrow(spacer1, Priority.ALWAYS);
            HBox.setHgrow(spacer2, Priority.ALWAYS);
            HBox.setHgrow(spacer3, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {



                }

            });

            this.getChildren().addAll(productName, spacer1,productPrice,spacer2,productCount,spacer3,button);
        }

        public int getProductId(){
            return productId;
        }
        public void setCount(int count){
            this.count = count;
        }
        public int getCount(){
            return count;
        }

        public String toString(){
            return item + " count: " + count;
        }

    }
    private void inquiryCartList() {
        System.out.println("Start downloading cart list from server");
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("toSend", true);
        jsonToSend.put("operation", "giveCartList");
        jsonToSend.put("userId", 1);
        ClientApp.controllerToClient(jsonToSend);
        System.out.println("Json sended");


        String orderedCartList = "";


        while (true) {
            if (Objects.equals(ClientApp.ClientToController().optString("toSend"), "false")) {
                orderedCartList = ClientApp.ClientToController().optString("cartList");
                System.out.println("Received cart list: " + orderedCartList);
                break;
            }
        }

        if (orderedCartList != null && !orderedCartList.isEmpty()) {
            JSONArray orderedCartJson = new JSONArray(orderedCartList);
            cartListData.clear();

            for (int i = 0; i < orderedCartJson.length(); i++) {
               JSONObject currentProductFromCart = orderedCartJson.getJSONObject(i);
                System.out.println(currentProductFromCart);

                int count = (int) currentProductFromCart.get("count");
                //System.out.println("Count: "+ count);

                JSONObject jsonProduct = (JSONObject) currentProductFromCart.get("product");
                //System.out.println("Product: " +product);
                String name = (String) jsonProduct.get("name");
                int price = (int) jsonProduct.get("price");
                int id = (int) jsonProduct.get("id");
                Product product = new Product(id,name,price);

                ProductCartFX item = new ProductCartFX(product,count);
                cartListData.add(item);



            }

            cartList.setItems(cartListData);

        } else {
            System.out.println("Cart is empty");
        }
        System.out.println("Finished downloading cart list from server");


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
            JSONArray orderedProdJson = new JSONArray(orderedProductList);

            productsListData.clear();

            for (int i = 0; i < orderedProdJson.length(); i++) {
                JSONObject currentProduct = orderedProdJson.getJSONObject(i);
                int id = (int) currentProduct.get("id");
                String name = (String) currentProduct.get("name");
                int price = (int) currentProduct.get("price");
                Product product = new Product(id,name,price);
                ProductFX item = new ProductFX(product);
                productsListData.add(item);



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
        cartList.setItems(cartListData);

    }
}
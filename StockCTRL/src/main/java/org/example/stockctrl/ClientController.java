package org.example.stockctrl;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;


public class ClientController implements Initializable {

    @FXML
    private TextField userName = new TextField();
    @FXML
    private TextField userSurname;
    @FXML
    private TextField userAddress;
    @FXML
    private Label incorrectPass;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    private boolean isLogged = false;
    @FXML
    private TextField registerUserName;
    @FXML
    private TextField registerUserSurname;
    @FXML
    private TextField registerUserLogin;
    @FXML
    private TextField registerUserPassword;

    @FXML
    private Label cartListSum = new Label();
    @FXML
    private ListView<ProductFX> productsList = new ListView<>();
    private final ObservableList<ProductFX> productsListData = FXCollections.observableArrayList();
    @FXML
    private ListView<ProductCartFX> cartList = new ListView<>();
    private final ObservableList<ProductCartFX> cartListData = FXCollections.observableArrayList();
    @FXML
    private ListView<OrderFX> orderList = new ListView<>();
    private final ObservableList<OrderFX> orderListData = FXCollections.observableArrayList();


    @FXML
    public void switchSceneToMainViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(main.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }
    @FXML
    public void switchSceneToRegistrationViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("register-view.fxml"));
        Scene scene = new Scene(main.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }
    @FXML
    public void switchSceneToRegistrationVievREGISTER(ActionEvent event) throws IOException {
        FXMLLoader waitingLoader = new FXMLLoader(ClientApp.class.getResource("waiting-view.fxml"));
        Scene waitingScene = new Scene(waitingLoader.load());
        waitingScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(waitingScene);

        CompletableFuture.runAsync(() -> {
            registerUser();

            Platform.runLater(() -> {
                    try {
                        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("start-view.fxml"));
                        Scene scene = new Scene(main.load());
                        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            });


        });
    }

    @FXML
    public void switchSceneToMainVievLOGIN(ActionEvent event) throws IOException {
        FXMLLoader waitingLoader = new FXMLLoader(ClientApp.class.getResource("waiting-view.fxml"));
        Scene waitingScene = new Scene(waitingLoader.load());
        waitingScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(waitingScene);

        CompletableFuture.runAsync(() -> {
                checkLogin();

            Platform.runLater(() -> {
                if (isLogged) {
                    try {
                        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("main-view.fxml"));
                        Scene scene = new Scene(main.load());
                        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    try {
                        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("start-view.fxml"));
                        Scene scene = new Scene(main.load());
                        scene.getStylesheets().add(getClass().getResource("/styling.css").toExternalForm());
                        ClientController controller = main.getController();
                        controller.showIncorrectPass();
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        });
    }

    public void showIncorrectPass() {
        incorrectPass.setOpacity(1);
    }

    @FXML
    public void switchSceneToStartViev(ActionEvent event) throws IOException {
        FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("start-view.fxml"));
        Scene scene = new Scene(main.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }


    @FXML
    public void switchSceneToProductViev(ActionEvent event) throws IOException {
        FXMLLoader waitingLoader = new FXMLLoader(ClientApp.class.getResource("waiting-view.fxml"));
        Scene waitingScene = new Scene(waitingLoader.load());
        waitingScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(waitingScene);


        CompletableFuture.runAsync(() -> {


            Platform.runLater(() -> {
                try {
                    FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("products-view.fxml"));
                    Scene scene = new Scene(main.load());
                    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
                    ClientController controller = main.getController();
                    controller.inquiryProductsList();
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

    }

    @FXML
    public void switchSceneToOrderViev(ActionEvent event) throws IOException {
        FXMLLoader waitingLoader = new FXMLLoader(ClientApp.class.getResource("waiting-view.fxml"));
        Scene waitingScene = new Scene(waitingLoader.load());
        waitingScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(waitingScene);


        CompletableFuture.runAsync(() -> {

            Platform.runLater(() -> {
                try {
                    FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("order-view.fxml"));
                    Scene scene = new Scene(main.load());
                    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
                    ClientController controller = main.getController();
                    controller.inquiryOrderList();
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    @FXML
    public void switchSceneToSettingsViev(ActionEvent event) throws IOException {
        FXMLLoader waitingLoader = new FXMLLoader(ClientApp.class.getResource("waiting-view.fxml"));
        Scene waitingScene = new Scene(waitingLoader.load());
        waitingScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(waitingScene);


        CompletableFuture.runAsync(() -> {


            Platform.runLater(() -> {
                try {
                    FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("settings-view.fxml"));
                    Scene scene = new Scene(main.load());
                    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
                    ClientController controller = main.getController();
                    controller.loadUserDetails();
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    @FXML
    public void switchSceneToCartViev(ActionEvent event) throws IOException {
        FXMLLoader waitingLoader = new FXMLLoader(ClientApp.class.getResource("waiting-view.fxml"));
        Scene waitingScene = new Scene(waitingLoader.load());
        waitingScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(waitingScene);


        CompletableFuture.runAsync(() -> {

            Platform.runLater(() -> {
                try {
                    FXMLLoader main = new FXMLLoader(ClientApp.class.getResource("cart-view.fxml"));
                    Scene scene = new Scene(main.load());
                    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styling.css")).toExternalForm());
                    ClientController controller = main.getController();
                    controller.inquiryCartList();
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });


    }



    //LOGIC
    @FXML
    public void buyProducts(ActionEvent event) {
        if (!cartListData.isEmpty()) {


            JSONObject json = new JSONObject();
            json.put("toSend", true);
            json.put("operation", "buy");

            JSONObject orderList = new JSONObject();
            orderList.put("userId", ClientApp.getUserID());
            orderList.put("date", LocalDate.now());
            orderList.put("status", "to pack");

            JSONArray orderListProducts = new JSONArray();

            for (ProductCartFX prod : cartListData) {
                orderListProducts.put(prod.toJson());
            }

            orderList.put("orderList", orderListProducts);
            System.out.println("OrderList:" + orderList);


            json.put("order", orderList.toString());

            ClientApp.controllerToClient(json);


            while (true) {
                if (Objects.equals(ClientApp.ClientToController().optString("toSend"), "false")) {
                    System.out.println("Clear cart list");
                    JSONObject jsonToSend = new JSONObject();
                    jsonToSend.put("toSend", true);
                    jsonToSend.put("operation", "deleteCartList");
                    jsonToSend.put("userId", ClientApp.getUserID());
                    ClientApp.controllerToClient(jsonToSend);

                    while (true) {
                        if (Objects.equals(ClientApp.ClientToController().optString("toSend"), "false")) {
                            try {
                                switchSceneToCartViev(event);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                    }


                    System.out.println("Finish deleting cartList");
                    break;
                }
            }
        } else {
            System.out.println("Cart List is empty");
        }


    }

    @FXML
    public void loadUserDetails() {
        System.out.println("Start downloading user details from server");
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("toSend", true);
        jsonToSend.put("operation", "giveUserDetails");
        jsonToSend.put("userId", ClientApp.getUserID());
        ClientApp.controllerToClient(jsonToSend);

        while (true) {
            if (Objects.equals(ClientApp.ClientToController().optString("toSend"), "false")) {
                System.out.println("Received user details: " + ClientApp.ClientToController());

                userName.setText(ClientApp.ClientToController().optString("name"));
                userSurname.setText(ClientApp.ClientToController().optString("surname"));
                userAddress.setText(ClientApp.ClientToController().optString("address"));
                break;

            }
        }

    }

    @FXML
    public void changeUserDetails(ActionEvent e) {
         JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("toSend", true);
        jsonToSend.put("operation", "changeUserDetails");

        JSONObject userDetails = new JSONObject();
        userDetails.put("id", ClientApp.getUserID());
        userDetails.put("name", userName.getText());
        userDetails.put("surname", userSurname.getText());
        userDetails.put("address", userAddress.getText());

        jsonToSend.put("userDetails", userDetails);

        ClientApp.controllerToClient(jsonToSend);
    }

    public static class OrderFX extends HBox {
        Label productName = new Label();
        Label productPrice = new Label();
        Label productCount = new Label();
        List<Product> orderList;

        OrderFX(List<Product> orderList, int id, String status) {
            super();
            this.orderList = orderList;

            StringBuilder productNameText = new StringBuilder();
            StringBuilder productPriceText = new StringBuilder();
            StringBuilder productCountText = new StringBuilder();

            productNameText.append("Order ID: \n");
            productPriceText.append(id).append("\n");
            productCountText.append("\n");

            productNameText.append("Current status: \n \n");
            productPriceText.append(status).append("\n \n");
            productCountText.append("\n \n");

            productNameText.append("Products name \n");
            productPriceText.append("Price \n");
            productCountText.append("Count \n");
            for (Product product : orderList) {
                productNameText.append(product.getName());
                productNameText.append("\n");
                productPriceText.append(BigDecimal.valueOf(product.getPrice() * 0.24).setScale(2, RoundingMode.HALF_UP)).append("$");
                productPriceText.append("\n");
                productCountText.append(product.getCount());
                productCountText.append("\n");


            }
            productNameText.append("\n");
            productPriceText.append("\n");
            productCountText.append("\n");
            productName.setText(String.valueOf(productNameText));
            productPrice.setText(String.valueOf(productPriceText));
            productCount.setText(String.valueOf(productCountText));



            Region spacer1 = new Region();
            Region spacer2 = new Region();
            spacer2.setMinWidth(100);
            HBox.setHgrow(spacer1, Priority.ALWAYS);

            this.getChildren().addAll(productName, spacer1, productPrice, spacer2, productCount);
        }



    }

    public static class ProductFX extends HBox {
        Label productName = new Label();
        Label productPrice = new Label();
        Button button = new Button("Add to cart");
        Product item;

        ProductFX(Product product) {
            super();
            item = product;

            productName.setText(product.getName());
            BigDecimal price = BigDecimal.valueOf(product.getPrice() * 0.24).setScale(2, RoundingMode.HALF_UP);
            productPrice.setText(price + "$");
            Region spacer1 = new Region();
            Region spacer2 = new Region();
            spacer1.setMinWidth(20);
            spacer2.setMinWidth(80);
            HBox.setHgrow(spacer1, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Start adding product to cart");

                    JSONObject product = new JSONObject();
                    product.put("id", item.getId());
                    product.put("name", item.getName());
                    product.put("price", item.getPrice());


                    JSONObject jsonToSend = new JSONObject();
                    jsonToSend.put("toSend", true);
                    jsonToSend.put("operation", "addProductToCart");
                    jsonToSend.put("userId",  ClientApp.getUserID());
                    jsonToSend.put("product", product.toString());
                    ClientApp.controllerToClient(jsonToSend);
                    System.out.println("Finish adding product to cart");
                }

            });

            this.getChildren().addAll(productName, spacer1, productPrice, spacer2, button);
        }


        public String toString() {
            int count = 0;
            return item + " count: " + count;
        }

    }

    public class ProductCartFX extends HBox {
        Label productName = new Label();
        Label productPrice = new Label();
        Button button = new Button("Delete");
        Label productCount = new Label();
        Product item;
        BigDecimal ProductPrice;
        private final int count;

        ProductCartFX(Product product, int count) {
            super();
            item = product;
            this.count = count;

            productName.setText(product.getName());
            ProductPrice = BigDecimal.valueOf(product.getPrice() * 0.24).setScale(2, RoundingMode.HALF_UP);
            productPrice.setText(ProductPrice + "$");
            productCount.setText("Count: " + count);
            Region spacer1 = new Region();
            Region spacer2 = new Region();
            Region spacer3 = new Region();
            spacer2.setMinWidth(100);
            spacer3.setMinWidth(80);
            HBox.setHgrow(spacer1, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Start deleting product to cart");

                    JSONObject productId = new JSONObject();
                    productId.put("id", item.getId());


                    JSONObject jsonToSend = new JSONObject();
                    jsonToSend.put("toSend", true);
                    jsonToSend.put("operation", "deleteProductFromCart");
                    jsonToSend.put("userId", ClientApp.getUserID());
                    jsonToSend.put("productId", productId.toString());
                    ClientApp.controllerToClient(jsonToSend);

                    while (true) {
                        if (Objects.equals(ClientApp.ClientToController().optString("toSend"), "false")) {
                            try {
                                switchSceneToCartViev(event);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                    }


                    System.out.println("Finish deleting product to cart");


                }

            });

            this.getChildren().addAll(productName, spacer1, productPrice, spacer2, productCount, spacer3, button);
        }

        public BigDecimal getPrice() {
            return ProductPrice;
        }


        public int getCount() {
            return count;
        }

        public String toString() {
            return item + " count: " + count;
        }

        public JSONObject toJson() {
            JSONObject prod = new JSONObject();

            prod.put("id", item.getId());
            prod.put("name", item.getName());
            prod.put("price", item.getPrice());
            prod.put("count", count);

            return prod;
        }

    }

    private void registerUser() {
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("toSend", true);
        jsonToSend.put("operation", "registerUser");
        jsonToSend.put("userName", registerUserName.getText());
        jsonToSend.put("userSurname", registerUserSurname.getText());
        jsonToSend.put("userLogin", registerUserLogin.getText());
        jsonToSend.put("userPassword", registerUserPassword.getText());
        ClientApp.controllerToClient(jsonToSend);


        while (true) {
            if (Objects.equals(ClientApp.ClientToController().optString("toSend"), "false")) {
                System.out.println("Successful registration");
                break;

            }
        }

    }
    private void checkLogin() {
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("toSend", true);
        jsonToSend.put("operation", "checkLogin");
        jsonToSend.put("login", login.getText());
        jsonToSend.put("password", password.getText());
        ClientApp.controllerToClient(jsonToSend);


        while (true) {
            if (Objects.equals(ClientApp.ClientToController().optString("toSend"), "false")) {
                boolean orderedMessage = ClientApp.ClientToController().optBoolean("answerLogin");
                if (orderedMessage) {
                    System.out.println("You are logged");
                    isLogged = true;
                    ClientApp.setUserID(Integer.parseInt(ClientApp.ClientToController().optString("userId")));
                    System.out.println("User id: " + ClientApp.getUserID());
                    break;

                } else {
                    System.out.println("Wrong password or login");

                    break;
                }

            }
        }


    }

    private void inquiryOrderList() {
        System.out.println("Start downloading order list from server");
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("toSend", true);
        jsonToSend.put("operation", "giveOrdersList");
        jsonToSend.put("userId", ClientApp.getUserID());
        ClientApp.controllerToClient(jsonToSend);


        String orderedOrderList = "";


        while (true) {
            if (Objects.equals(ClientApp.ClientToController().optString("toSend"), "false")) {
                orderedOrderList = ClientApp.ClientToController().optString("ordersList");
                System.out.println("Received orders list: " + orderedOrderList);
                break;
            }
        }

        if (orderedOrderList != null && !orderedOrderList.isEmpty()) {
            JSONArray orderedProdJson = new JSONArray(orderedOrderList);
            orderListData.clear();

            for (int i = 0; i < orderedProdJson.length(); i++) {
                JSONObject currentOrder = orderedProdJson.getJSONObject(i);


                int id = (int) currentOrder.get("id");
                String status = (String) currentOrder.get("status");

                JSONArray products = new JSONArray((String) currentOrder.get("products"));

                List<Product> listProducts = new ArrayList<>();

                for (int j = 0; j < products.length(); j++) {
                    JSONObject currentProductFromOrder = products.getJSONObject(j);
                    String name = (String) currentProductFromOrder.get("name");
                    int price = (int) currentProductFromOrder.get("price");
                    int productId = (int) currentProductFromOrder.get("id");
                    int count = (int) currentProductFromOrder.get("count");


                    Product product = new Product(productId, name, price);
                    product.setCount(count);
                    listProducts.add(product);


                }
                OrderFX order = new OrderFX(listProducts, id, status);

                orderListData.add(order);
            }
            orderList.setItems(orderListData);

        } else {
            System.out.println("You don't have any orders yet");
        }
        System.out.println("Finished downloading order from server");


    }

    private void inquiryCartList() {
        System.out.println("Start downloading cart list from server");
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("toSend", true);
        jsonToSend.put("operation", "giveCartList");
        jsonToSend.put("userId", String.valueOf( ClientApp.getUserID()));
        ClientApp.controllerToClient(jsonToSend);


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

                int count = (int) currentProductFromCart.get("count");

                JSONObject jsonProduct = (JSONObject) currentProductFromCart.get("product");
                String name = (String) jsonProduct.get("name");
                int price = (int) jsonProduct.get("price");
                int id = (int) jsonProduct.get("id");
                Product product = new Product(id, name, price);

                ProductCartFX item = new ProductCartFX(product, count);
                cartListData.add(item);


            }

            cartList.setItems(cartListData);
            BigDecimal productsSum= BigDecimal.valueOf(0);

            for(ProductCartFX item : cartListData) {

                productsSum = productsSum.add(BigDecimal.valueOf(item.getCount()).multiply(item.getPrice()));
            }
            System.out.println("Sum of products: " + productsSum);
            cartListSum.setText("Sum: " + productsSum + "$");


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
                Product product = new Product(id, name, price);
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
        orderList.setItems(orderListData);

    }
}
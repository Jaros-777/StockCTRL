module org.example.stockctrl {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.stockctrl to javafx.fxml;
    exports org.example.stockctrl;
}
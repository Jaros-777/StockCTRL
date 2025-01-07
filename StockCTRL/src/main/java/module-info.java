module org.example.stockctrl {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    opens hibernate to org.hibernate.orm.core;

    opens org.example.stockctrl to javafx.fxml;
    exports org.example.stockctrl;
}
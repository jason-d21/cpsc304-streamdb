module com.example.cpsc304streamdb {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cpsc304streamdb to javafx.fxml;
    exports com.example.cpsc304streamdb;
}
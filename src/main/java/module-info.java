module com.example.cpsc304streamdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.cpsc304streamdb to javafx.fxml;
    exports com.example.cpsc304streamdb;
}
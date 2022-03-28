module DatabaseProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens DatabaseProject to javafx.fxml;
    exports DatabaseProject;
}
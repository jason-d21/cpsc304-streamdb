module DatabaseProject {
    requires javafx.controls;
    requires javafx.fxml;


    opens DatabaseProject to javafx.fxml;
    exports DatabaseProject;
}
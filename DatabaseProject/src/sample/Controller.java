package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ComboBox<String> templates;

    @FXML
    void getTemplates(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        templates.getItems().addAll("Insert", "Delete", "Update", "Select");
    }
}

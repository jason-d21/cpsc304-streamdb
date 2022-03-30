package DatabaseProject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class ControllerLogin {

    @FXML
    private Button loginButton;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    private Text alertText;

    @FXML
    private CheckBox checkboxInitialize;

    public static MySQL mysql;

    @FXML
    void login(MouseEvent event) throws Exception {
        String username = this.username.getText();
        String password = this.password.getText();

        if (username.equals("") || password.equals("")) {
            return;
        }

        // At the start of the session, enter the username and password for the connection to log in
        // default username for MySQL connection is "root"

        String scriptName = "sample.sql"; // script to run DDL and populate database
        String query = "SELECT * FROM testtable1"; // sample query

        try {
            mysql = new MySQL(username, password);

            if (checkboxInitialize.isSelected()) {
                mysql.runScript(scriptName); // run script to initialize database
            }

            Parent root = FXMLLoader.load(getClass().getResource("databaseUI.fxml"));
            Stage window = (Stage) loginButton.getScene().getWindow();
            window.setScene(new Scene(root));
        }
        catch (Exception e) {
            alertText.setText("Error: Login failed: " + e.getMessage());
        }
    }
}
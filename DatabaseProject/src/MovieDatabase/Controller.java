package MovieDatabase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ComboBox<String> templates;

    @FXML
    private ComboBox<String> defaultQueries;

    @FXML
    private TextArea terminal;

    @FXML
    private TextArea output;

    @FXML
    void RunCommand(MouseEvent event) {
        String command = terminal.getText().replace("\n", "").replace("\r", "");
        output.setText("PLACEHOLDER OUTPUT");
    }

    @FXML
    void SelectTemplate(ActionEvent event) {
        String option = templates.getSelectionModel().getSelectedItem();
        if (option.equals("Insert")) {
            terminal.setText("INSERT INTO table_name\n" +
                    "VALUES (value1, value2, value3, ...);");
        } else if (option.equals("Delete")) {
            terminal.setText("DELETE FROM table_name\n" + "WHERE condition;");
        } else if (option.equals("Update")) {
            terminal.setText("UPDATE table_name\n" +
                    "SET column1 = value1, column2 = value2, ...\n" +
                    "WHERE condition;");
        } else if (option.equals("Select")) {
            terminal.setText("SELECT column1, column2, ...\n" +
                    "FROM table_name\n" +
                    "WHERE condition;");
        }
    }

    @FXML
    void SelectQuery(ActionEvent event) {
        String option = defaultQueries.getSelectionModel().getSelectedItem();
        if (option.equals("Query1")) {
            terminal.setText("Query1");
        } else if (option.equals("Query2")) {
            terminal.setText("Query2");
        } else if (option.equals("Query3")) {
            terminal.setText("Query3");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        templates.getItems().addAll("Insert", "Delete", "Update", "Select");
        defaultQueries.getItems().addAll("Query1", "Query2", "Query3");
    }
}

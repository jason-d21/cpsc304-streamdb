package DatabaseProject;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerDatabase implements Initializable {

    @FXML
    private ComboBox<String> templates;

    @FXML
    private ComboBox<String> defaultQueries;

    @FXML
    private TextArea terminal;

    @FXML
    private TextArea output;

    private String[] statements = {"insert", "delete", "update"};

    private MySQL mysql;

    private List<String> queries;

    @FXML
    void RunCommand(MouseEvent event) {
        String command = terminal.getText().replace("\n", " ").replace("\r", " ").trim();
        for (String s: statements) {
            if (command.toLowerCase().startsWith(s)) {
                try {
                    mysql.runStatement(command);
                    output.setText("SUCCESSFUL");
                } catch (Exception e) {
                    output.setText(e.getMessage());
                }
                return;
            }
        }

        try {
            Pair<List<String>, List<List<String>>> queryResult = mysql.runQuery(command);
            String outputResult = "";

            for (String s: queryResult.left) {
                outputResult += s + " ";
            }
            outputResult += "\n";
            for (List<String> j: queryResult.right) {
                String tuple = "";
                for (String s: j) {
                    tuple += s + " ";
                }
                outputResult += tuple + "\n";
            }
            output.setText(outputResult);
        } catch (Exception e) {
            output.setText(e.getMessage());
        }
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
            terminal.setText(queries.get(0));
        } else if (option.equals("Query2")) {
            terminal.setText(queries.get(1));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        templates.getItems().addAll("Insert", "Delete", "Update", "Select");
        defaultQueries.getItems().addAll("Query1", "Query2");

        mysql = ControllerLogin.mysql;

        try {
            queries = mysql.readScript("queries.sql");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
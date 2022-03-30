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
                    output.setText("COMMAND SUCCESSFUL");
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
                outputResult += String.format("%" + (-50) + "s", s) + " ";
            }

            outputResult += "\n";
            int l = outputResult.length();
            for (int i = 0; i < l; i++) {
                outputResult += "-";
            }

            outputResult += "\n";
            for (List<String> j: queryResult.right) {
                String tuple = "";
                for (String s: j) {
                    tuple += String.format("%" + (-50) + "s", s) + " ";
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
        if (option.equals("Selection")) {
            terminal.setText(queries.get(0));
        } else if (option.equals("Projection")) {
            terminal.setText(queries.get(1));
        } else if (option.equals("Join")) {
            terminal.setText(queries.get(2));
        } else if (option.equals("Aggregation")) {
            terminal.setText(queries.get(3));
        } else if (option.equals("Nested aggregation with group by")) {
            terminal.setText(queries.get(4));
        } else if (option.equals("Division")) {
            terminal.setText(queries.get(5));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        templates.getItems().addAll("Insert", "Delete", "Update", "Select");
        defaultQueries.getItems().addAll("Selection", "Projection", "Join", "Aggregation",
                "Nested aggregation with group by", "Division");

        mysql = ControllerLogin.mysql;

        try {
            queries = mysql.readScript("queries.sql");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
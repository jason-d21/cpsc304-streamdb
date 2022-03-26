// Code refactored from https://www.vogella.com/tutorials/MySQLJava/article.html

package sql;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MySQL {
    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String SCHEMA_DEFAULT = "cpsc304project";

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public MySQL(String username, String password) throws SQLException {
        try {
            connect = DriverManager.getConnection(URL+SCHEMA_DEFAULT, username, password);
            statement = connect.createStatement();
            System.out.println("Connection established...");
        }
        catch (SQLException e) {
            close();
            throw e;
        }
    }
    public MySQL(String username, String password, String schema) throws SQLException {
        try {
            connect = DriverManager.getConnection(URL+schema, username, password);
            statement = connect.createStatement();
            System.out.println("Connection established...");
        }
        catch (SQLException e) {
            close();
            throw e;
        }
    }

    public void runScript(String scriptName) throws FileNotFoundException, SQLException {
        File f = new File("./" + scriptName);
        Scanner reader = new Scanner(f);
        reader.useDelimiter(";");

        List<String> statements = new ArrayList<>();
        while (reader.hasNext()) {
            String stm = reader.next();
            statements.add(stm.trim());
        }

        for (String s : statements) {
            runStatement(s);
        }
    }

    private void runStatement(String stm) throws SQLException {
        statement.executeUpdate(stm);
        System.out.println("Statement executed:");
        System.out.println(stm);
        System.out.println();
    }

    private void writeMetaData() throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet() throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getString(2);
            String user = resultSet.getString("myuser");
            String website = resultSet.getString("webpage");
            String summary = resultSet.getString("summary");
            Date date = resultSet.getDate("datum");
            String comment = resultSet.getString("comments");
            System.out.println("User: " + user);
            System.out.println("Website: " + website);
            System.out.println("summary: " + summary);
            System.out.println("Date: " + date);
            System.out.println("Comment: " + comment);
        }
    }

    // You need to close the resultSet
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
}

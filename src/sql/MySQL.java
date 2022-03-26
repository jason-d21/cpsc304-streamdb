// Code refactored from https://www.vogella.com/tutorials/MySQLJava/article.html

package sql;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MySQL {
    private final String URL = "jdbc:mysql://localhost:3306/";  // check MySQL connection for localhost port
    private final String SCHEMA_DEFAULT = "cpsc304project"; // create this schema in the connection if using default

    private Connection connect;
    private Statement statement;
    private ResultSet resultSet;

    // Constructor using default schema
    public MySQL(String username, String password) throws SQLException {
        try {
            connect = DriverManager.getConnection(URL+SCHEMA_DEFAULT, username, password);
            statement = connect.createStatement();
            resultSet = null;
            System.out.println("Connection established...");
        }
        catch (SQLException e) {
            close();
            throw e;
        }
    }

    // Constructor with schema parameter
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

    // Take the name of an SQL script in the parent folder and run all statements in the script
    public void runScript(String scriptName) throws FileNotFoundException, SQLException {
        List<String> statements = readScript(scriptName);
        for (String s : statements) {
            runStatement(s);
        }
    }

    // Read SQL script and returns a list of statements in the script
    private List<String> readScript(String scriptName) throws FileNotFoundException {
        File f = new File("./" + scriptName);
        Scanner reader = new Scanner(f);
        reader.useDelimiter(";");

        List<String> statements = new ArrayList<>();
        while (reader.hasNext()) {
            String stm = reader.next();
            stm = stm.trim();
            if (!stm.isEmpty()) statements.add(stm);
        }

        return statements;
    }

    // For executing statements that do not return anything: DDL, INSERT, UPDATE, DELETE
    // Executed statement will be printed out to console
    public void runStatement(String stm) throws SQLException {
        statement.executeUpdate(stm);
        System.out.println("Statement executed:");
        System.out.println(stm);
        System.out.println();
    }

    // Takes in a query as a string, run it, and return the results
    // The result is a pair where:
    // - pair.left is the list of all attributes/column names in the result
    // - pair.right is the list of tuples in the result
    //      + each tuple is a list of values whose index corresponds to the index of list of attributes
    public Pair<List<String>, List<List<String>>> runQuery(String query) throws SQLException {
        resultSet = statement.executeQuery(query);
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        List<String> attributes = new ArrayList<>();
        for (int i = 1; i <= columnsNumber; i++) {
            attributes.add(rsmd.getColumnLabel(i));
        }

        List<List<String>> tuples = new ArrayList<>();
        while (resultSet.next()) {
            List<String> tuple = new ArrayList<>();
            for (int i = 1; i <= columnsNumber; i++) {
                tuple.add(resultSet.getString(i));
            }
            tuples.add(tuple);
        }

        return new Pair<>(attributes, tuples);
    }

    // Close connection. Need to be called at the end a session whenever an instance is invoked
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
        }
        catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}

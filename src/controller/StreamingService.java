package controller;

import sql.MySQL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StreamingService {
    public static void main(String[] args) throws Exception {
        String scriptName = "sample.sql";

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = myObj.nextLine();

        System.out.println("Enter password:");
        String password = myObj.nextLine();

        MySQL mysql = new MySQL(username, password);

        try {
            mysql.runScript(scriptName);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            mysql.close();
        }

    }
}

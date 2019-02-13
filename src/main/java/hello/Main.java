package hello;
import utilities.DBHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("Could not find driver for database");
        }

        DBHandler dbHandler = new DBHandler();

    }


}

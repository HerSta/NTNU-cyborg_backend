package hello;
import utilities.DBHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {


    public static void main(String[] args) {

        DBHandler dbHandler = new DBHandler();
        String file = "C:\\Users\\Kim Erling\\Documents\\2017-10-20_MEA2_100000rows_10sec";
        dbHandler.insertCSVMatrixIntoDB();
    }


}

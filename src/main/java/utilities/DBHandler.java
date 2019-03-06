package utilities;

import org.hibernate.validator.cfg.context.ContainerElementConstraintMappingContext;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBHandler {
    private final String username = "kimera_k3ah";
    private final String  password = "passord132";
    //private final String url = "mysql.stud.ntnu.no";
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://mysql.stud.ntnu.no:3306/kimera_k3ah";

    public DBHandler(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("Could not find driver for database");
        }
    }

    public Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        }catch (SQLException ex){
            ex.getErrorCode();
            ex.getSQLState();
        }
        if(conn == (null)){
            System.out.println("Could not create connection");
            return null;
        }
        System.out.println("Connected to database");
        return conn;

    }

    public boolean insertCSVMatrixIntoDB(){
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getFile();
        String directory = dialog.getDirectory();
        System.out.println(file + " chosen.");
        System.out.println(directory + " directory");
        String query = "INSERT into cellData (cellID, timeStamp, value) values (?, ?, ?)";

        try{

            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = null;

            Scanner scanner = new Scanner(new File(directory+file));
            String[] cellIDArray = scanner.nextLine().split(",");
            String timestamp = "";
            int counter = 0;
            System.out.println("Starting read of file");
            System.out.println(cellIDArray[0] + cellIDArray[1]);
            while(scanner.hasNextLine()){
                System.out.println("Starting scan");
                String line = scanner.nextLine();
                String[] values = line.split(",");
                timestamp = values[0];
                for (int x = 1; x < values.length; x++){
                    System.out.println((x-1) + " values processed");
                    statement.setString(1,cellIDArray[x]);
                    statement.setString(2, timestamp);
                    statement.setString(3, values[x]);
                    statement.execute();
                }
                counter++;
                System.out.println(counter + " lines processed");
            }
            return true;



        }catch (SQLException ex){
            System.out.println("SQL error");
            ex.getErrorCode();
            ex.getSQLState();
            return false;
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }
    }

    public List<String> selectAllFromTable(String table) {
        Connection conn = this.getConnection();
        System.out.println(conn.toString());
        Statement stmt = null;
        ResultSet rs = null;

        List<String> results = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM "+table);
            while (rs.next()) {
                System.out.println(rs.getInt(1));
            }

            return null;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
        }

    }

    public List<String> get10kDataFromNode(String nodeID){
        List<String> results = new ArrayList<>();
        String query = "SELECT * FROM cellData WHERE cellID = ?";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = null;
            statement.setString(1, nodeID);
            rs = statement.executeQuery();
            while(rs.next()){
                String value = rs.getString("value");
                //System.out.println(value);
                results.add(value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
}

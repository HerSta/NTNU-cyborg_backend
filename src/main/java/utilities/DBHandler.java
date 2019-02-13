package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler {
    String username = "kimera_k3ah";
    String password = "passord132";
    String url = "mysql.stud.ntnu.no";
    String driver = "com.mysql.jdbc.Driver";

    public Connection getConnection() throws SQLException{
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        }catch (SQLException ex){
            ex.getErrorCode();
            ex.getSQLState();
        }
            System.out.println("Connected to database");
        return conn;

    }
}

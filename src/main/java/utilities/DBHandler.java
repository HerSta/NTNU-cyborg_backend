package utilities;

import java.sql.*;
import java.util.List;

public class DBHandler {
    private final String username = "kimera_k3ah";
    private final String  password = "passord132";
    private final String url = "mysql.stud.ntnu.no";
    private final String driver = "com.mysql.jdbc.Driver";


    public Connection getConnection(){
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

    public List<String> selectAllFromTable(String table) {
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT foo FROM bar");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (stmt.execute("SELECT foo FROM bar")) {
                rs = stmt.getResultSet();
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
}

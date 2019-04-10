package utilities;

import org.hibernate.validator.cfg.context.ContainerElementConstraintMappingContext;

import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;
import java.util.List;


public class DBHandler {

    String hostName = "kyborg-db.database.windows.net"; // update me
    String dbName = "kyborg-db"; // update me
    String user = "kyborg"; // update me
    String password = "Kyburger2019"; // update me
    String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;"
            + "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);

    /*
    private final String username = "kyborg";//"kimera_k3ah";
    private final String  password = "Kyburger2019";//"passord132";
    //private final String url = "mysql.stud.ntnu.no";
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:sqlserver://kyborg-db.database.windows.net:1433/kyborg-db";//"jdbc:mysql://mysql.stud.ntnu.no:3306/kimera_k3ah";

    private static final String constring = "jdbc:sqlserver://kyborg-db.database.windows.net:1433;database=kyborg-db;user=kyborg@kyborg-db;password={Kyburger2019};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    String connectionUrl = "jdbc:sqlserver://kyborg-db.database.windows.net:1433/kyborg-db;database=kyborg-db;user=kyborg@kyborg-db;password={Kyburger2019};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

     */
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
            conn = DriverManager.getConnection(url);
        }catch (SQLException ex){
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getSQLState());
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

        String query = "INSERT into Data (ID, ID2, base, timeStamp, value, experimentID) values (?, ?, ?, ?, ?, ?)";

        try{

            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = null;

            Random r = new Random();
            int experimentID = r.nextInt(100) + 1;


            //47 (ID=0) [pV]
            Scanner scanner = new Scanner(new File(directory+file));
            String[] cellIDArray = scanner.nextLine().split(",");

            for(int x = 1; x < cellIDArray.length; x++){
                if(cellIDArray[x].contains("Ref")){
                    cellIDArray[x] = "99,99,Ref";
                }else {
                    String cell = cellIDArray[x];
                    String[] cellSplitted = cell.split(" ");
                    String ID = cellSplitted[0];
                    String ID2 = cellSplitted[1];

                    ID2 = ID2.substring(4, ID2.length()-1);

                    String base = cellSplitted[2].substring(1, 3);

                    cellIDArray[x] = ID+","+ID2+","+base;
                }


            }
            String timestamp = "";
            int counter = 0;
            System.out.println("Starting read of file" + file);
            System.out.println(cellIDArray[0] + cellIDArray[1]);
            while(scanner.hasNextLine()){
                counter++;
                String line = scanner.nextLine();
                String[] values = line.split(",");
                timestamp = values[0];
                for (int x = 1; x < values.length; x++){
                    //System.out.println((x-1) + " values processed");
                    String[] cellInfo = cellIDArray[x].split(",");

                    statement.setInt(1,Integer.parseInt(cellInfo[0]));
                    statement.setInt(2, Integer.parseInt(cellInfo[1]));
                    statement.setString(3, cellInfo[2]);
                    statement.setInt(4, Integer.parseInt(timestamp));
                    statement.setInt(5, Integer.parseInt(values[x]));
                    statement.setInt(6, (experimentID));
                    statement.addBatch();
                    //System.out.println(statement.toString());

                }
                if(counter %100 == 0){
                    statement.executeLargeBatch();
                    System.out.println(counter + " lines processed");
                }

            }
            System.out.println("Inserted "+counter+" lines into the DB");
            conn.close();
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

    public Map<Integer, Map<Integer, Integer>> getData(List<Integer> nodeIds, int startTime, int endTime){
        Map<Integer, Map<Integer, Integer>> resultMapMap = new HashMap<>();
        String query = "SELECT * FROM Data WHERE (ID = ?";
        for (int x = 1; x < nodeIds.size() ; x++){
            query += " OR ID = ?";
        }
        query += ") AND timeStamp >= ? AND timeStamp <= ?";
        query += " ORDER BY timeStamp";

        try{
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            for (int x = 0; x < nodeIds.size(); x++){
                resultMapMap.put(nodeIds.get(x), new HashMap<Integer, Integer>());
                statement.setInt(x+1, nodeIds.get(x));
            }
            statement.setInt(nodeIds.size()+1, startTime);
            statement.setInt(nodeIds.size()+2, endTime);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                resultMapMap
                        .get(resultSet.getInt("ID"))
                        .put(resultSet.getInt("timeStamp"),
                        resultSet.getInt("value"));
            }
            con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultMapMap;
    }
    public Map<String, List<Integer>> getNkDataFromNode(int nodeId, int K){
        Map<String, List<Integer>> resultMap = new HashMap<>();
        List<Integer> results = new ArrayList<>();
        List<Integer> timeStampList = new ArrayList<>();
        List<Integer> nodeList = new ArrayList<>();
        String query = "SELECT * FROM Data WHERE ID = ? ";
        /*for (int x = 2; x < nodeIdList.size(); x ++){
            query += " OR ID = ? ";
        }*/
        query += " ORDER BY timeStamp";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = null;
            //for (int x = 1; x < nodeIdList.size(); x++){
                //statement.setInt(x, (nodeIdList.get(x)));

            //}
            statement.setInt(1, (nodeId));
            rs = statement.executeQuery();
            int counter = 0;
            boolean positiveGrowth = true;
            int previousNumber = 0;
            int previousTimestamp = 0;
            int currentNumber = 0;
            //int previousNode = 0;
            while(rs.next()){
                if(counter == K*1000){
                    break;
                }
                //int node = rs.getInt("ID");
                int value = rs.getInt("value");
                int timestamp = rs.getInt("timeStamp");
                if(counter == 0){
                    previousNumber = value;
                    previousTimestamp = timestamp;
                    //previousNode = node;
                }
                /*if (counter % 3 == 0){
                    results.add(Integer.parseInt(value));
                }*/

                if(positiveGrowth){
                    currentNumber = (value);
                    if(currentNumber < (previousNumber)){
                        timeStampList.add(previousTimestamp);
                        results.add(previousNumber);
                        //nodeList.add(previousNode);
                        positiveGrowth = false;
                    }
                }else {
                    currentNumber = (value);
                    if(currentNumber > (previousNumber)){
                        timeStampList.add(previousTimestamp);
                        results.add(previousNumber);
                        //nodeList.add(previousNode);
                        positiveGrowth = true;
                    }
                }
                previousTimestamp = timestamp;
                previousNumber = value;
                //previousNode = node;

                //System.out.println(value);
                counter +=1;
                //System.out.println(counter);
            }
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        //System.out.println(results);
        //System.out.println(results.size());
        //resultMap.put("nodes", nodeList);
        resultMap.put("data", results);
        resultMap.put("timestamp", timeStampList);
        return resultMap;
    }

    public ArrayList<Integer> getNodes(){
        ArrayList<Integer> nodeList = new ArrayList<>();
        Connection conn = getConnection();
        String query = "SELECT distinct ID from Data";
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                nodeList.add(rs.getInt("ID"));
            }
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return nodeList;
    }
}

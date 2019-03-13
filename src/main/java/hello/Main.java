package hello;
import utilities.DBHandler;
import utilities.FileHandler;

import java.util.List;

public class Main {


    public static void main(String[] args) {

        DBHandler dbHandler = new DBHandler();
        FileHandler fileHandler = new FileHandler();
        String fileString = "C:\\Users\\Kim Erling\\Documents\\2017-10-20_MEA2_100000rows_10sec";
        int counter = 0;
        for (int node : dbHandler.getNodes()){
            dbHandler.getNkDataFromNodes(node, 2);
        }

        //fileHandler.ReadCSV();
        //dbHandler.insertCSVMatrixIntoDB();
        //dbHandler.get10kDataFromNode("47 (ID=0) [pV]");
        //List<String> nodelist = dbHandler.getNodes();
        //System.out.println(nodelist);
    }


}

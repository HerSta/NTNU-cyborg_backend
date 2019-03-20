package hello;
import utilities.DBHandler;
import utilities.FileHandler;

import java.util.List;
import java.util.Map;

public class Main {


    public static void main(String[] args) {

        DBHandler dbHandler = new DBHandler();
        FileHandler fileHandler = new FileHandler();
        String fileString = "C:\\Users\\Kim Erling\\Documents\\2017-10-20_MEA2_100000rows_10sec";
        int counter = 0;
        Map<Integer, Map<Integer, Integer>> resultMap = dbHandler.getData(dbHandler.getNodes(),100, 1000 );

        for ( int node : resultMap.keySet()){
            for(int time: resultMap.get(node).keySet()){
                System.out.println("size " +resultMap.get(node).size() +" : node "+node+" : time "+time);
            }
        }
        System.out.println(resultMap.size());

        //fileHandler.ReadCSV();
        //dbHandler.insertCSVMatrixIntoDB();
        //dbHandler.get10kDataFromNode("47 (ID=0) [pV]");
        //List<String> nodelist = dbHandler.getNodes();
        //System.out.println(nodelist);
    }


}

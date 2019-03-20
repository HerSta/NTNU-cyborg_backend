package hello;

import utilities.DBHandler;

import java.util.ArrayList;
import java.util.Map;

public class MultiSensorData {

    Map<Integer, Map<Integer, Integer>> resultMap;

    public MultiSensorData(String nodesString, int startTime, int endTime){
        System.out.println("Request for "+nodesString + " with start time "+startTime+" and end time "+endTime);
        ArrayList<Integer> nodeIDs;
        DBHandler dbHandler = new DBHandler();
        if(nodesString.equals("all")){
            nodeIDs = dbHandler.getNodes();


        }else {
            nodeIDs = new ArrayList<>();
            String[] nodesSplitted = nodesString.split("-");
            for (String node: nodesSplitted){
                nodeIDs.add(Integer.parseInt(node));
            }
        }

        this.resultMap = dbHandler.getData(nodeIDs, startTime, endTime);
    }

    public Map<Integer, Map<Integer, Integer>> getResultMap() {
        return resultMap;
    }
}

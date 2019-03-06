package hello;

import utilities.DBHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Sensordata {
    String nodeID;
    List<String> resultList;
    List<String> timestampList;




    public Sensordata(String queryString){
        DBHandler dbHandler = new DBHandler();
        nodeID = queryString;
        Map<String, List<String>> resultMap = dbHandler.get10kDataFromNode(queryString);
        resultList = resultMap.get("data");
        timestampList = resultMap.get("timestamp");
    }

    public List<String> getTimestampList() {
        return timestampList;
    }

    public List<String> getResultList() {
        return resultList;
    }

    public String getNodeID() {
        return nodeID;
    }
}

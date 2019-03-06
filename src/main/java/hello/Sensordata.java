package hello;

import utilities.DBHandler;

import java.util.ArrayList;
import java.util.List;


public class Sensordata {
    List<String> resultList;
    String nodeID;

    public Sensordata(String queryString){
        DBHandler dbHandler = new DBHandler();
        nodeID = queryString;
        resultList = dbHandler.get10kDataFromNode(queryString);
    }

    public List<String> getResultList() {
        return resultList;
    }

    public String getNodeID() {
        return nodeID;
    }
}

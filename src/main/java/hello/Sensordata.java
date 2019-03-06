package hello;

import utilities.DBHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Sensordata {
    String nodeID;
    List<String> resultList;
    List<String> timestampList;
    String tUnit = "uS";
    String vUnit = "pV";




    public Sensordata(String nodeRequestID){
        nodeID = nodeRequestID;
        DBHandler dbHandler = new DBHandler();
        List<String> nodeList = dbHandler.getNodes();
        String queryString = "";
        for(String nodeString : nodeList ){
            if(nodeRequestID.matches(nodeString.split(" ")[0])){
                queryString = nodeString;

            }
        }
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

    public String gettUnit() {
        return tUnit;
    }

    public String getvUnit() {
        return vUnit;
    }
}

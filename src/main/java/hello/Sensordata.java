package hello;

import utilities.DBHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Sensordata {
    int nodeID;
    List<Integer> resultList;
    List<Integer> timestampList;

    //List<Integer> nodeList;
    String tUnit = "uS";
    String vUnit = "pV";




    public Sensordata(String nodeRequestID){
        try {
            nodeID = Integer.parseInt(nodeRequestID);
        }catch (Exception e){
            nodeID = Integer.parseInt(nodeRequestID.split(" ")[0]);
        }
        DBHandler dbHandler = new DBHandler();
        List<Integer> nodeList = dbHandler.getNodes();
        //List<Integer> nodeStringList = new ArrayList<>();
        int queryInt = 0;
        for(int nodeId : nodeList ){
            if(nodeId == nodeID){
                queryInt = nodeID;
            }
            /*for(String node : nodeRequestID.split(" ")){
                if(nodeId==nodeID){
                    nodeStringList.add(nodeId);

                }

            }*/
        }
        Map<String, List<Integer>> resultMap = dbHandler.getNkDataFromNode(queryInt, 2);

        resultList = resultMap.get("data");
        timestampList = resultMap.get("timestamp");
        //nodeList = resultMap.get("nodes");
    }

    public List<Integer> getTimestampList() {
        return timestampList;
    }

    public List<Integer> getResultList() {
        return resultList;
    }

    public Integer getNodeID() {
        return nodeID;
    }

    public String gettUnit() {
        return tUnit;
    }

    public String getvUnit() {
        return vUnit;
    }

    /*public List<Integer> getNodeList() {
        return nodeList;
    }*/
}

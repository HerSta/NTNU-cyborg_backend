package hello;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;
import utilities.DBHandler;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class DBController {

    private static final String queryedNode= "";
    private static final String numberOfNodes = "all";
    private static final ArrayList list = new ArrayList<Integer>(){{add(45);}};
    //private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/sensordata")
    public Sensordata data(@RequestParam(value="node", defaultValue="47") String queryedNode) {
        return new Sensordata(queryedNode);
    }

    @RequestMapping("/nodes")
    public List<Integer> nodes(@RequestParam(value = "value", defaultValue = "all") String numberOfNodes){
        DBHandler dbHandler = new DBHandler();
        return dbHandler.getNodes();
    }

    @RequestMapping("/getData")
    public MultiSensorData
    data(
            @RequestParam(value="nodeList", defaultValue = "all") String nodesString,
            @RequestParam(value="startTime", defaultValue = "0") int startTime,
            @RequestParam(value="endTime", defaultValue = "20000") int endTime)
    {
        DBHandler dbHandler = new DBHandler();
        return new MultiSensorData(nodesString, startTime, endTime);
        //return dbHandler.getData(dbHandler.getNodes(), 0, 1000);

    }

}


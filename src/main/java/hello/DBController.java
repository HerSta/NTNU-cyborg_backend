package hello;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;
import utilities.DBHandler;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class DBController {

    private static final String queryedNode = "47 (ID=0) [pV]";
    private static final String numberOfNodes = "all";
    //private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/sensordata")
    public Sensordata data(@RequestParam(value="node", defaultValue="47") String queryedNode) {
        return new Sensordata(queryedNode);
    }

    @RequestMapping("/nodes")
    public List<String> nodes(@RequestParam(value = "value", defaultValue = "all") String numberOfNodes){
        DBHandler dbHandler = new DBHandler();
        return dbHandler.getNodes();
    }

}


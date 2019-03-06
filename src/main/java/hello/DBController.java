package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DBController {

    private static final String queryedNode = "47 (ID=0) [pV]";
    //private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/sensordata")
    public Sensordata data(@RequestParam(value="queryedNode", defaultValue="47 (ID=0) [pV]") String queryedNode) {
        return new Sensordata(queryedNode);
    }
}


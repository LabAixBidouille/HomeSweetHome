package org.labaix;

import org.labaix.mqtt.ReceiverSample;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by nicolas on 22/01/15.
 */
@RestController
@RequestMapping("/data")
public class DataController  {

    public static int temperature;
    public static int humidite;

    @RequestMapping(value = "/temperature", method = RequestMethod.GET, produces = "application/json")
    public String temperature() {
        return "{\"x\":" + System.currentTimeMillis() +", \"y\":"+ temperature +"}";
    }

    @RequestMapping(value = "/humidite", method = RequestMethod.GET, produces = "application/json")
    public String humidite() {
        return "{\"x\":" + System.currentTimeMillis() +", \"y\":"+ humidite +"}";
    }

    static {
        ReceiverSample app = new ReceiverSample();
        app.startReception();
    }
}

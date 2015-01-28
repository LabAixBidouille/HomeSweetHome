package org.labaix.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger log = LoggerFactory.getLogger(DataController.class);

    public static int temperature;
    public static int humidite;

    @RequestMapping(value = "/temperature", method = RequestMethod.GET, produces = "application/json")
    public String temperature() {
            int x = 0;
            return "{\"x\":" + x +", \"y\":"+ humidite +"}";
    }

    @RequestMapping(value = "/humidite", method = RequestMethod.GET, produces = "application/json")
    public String humidite() {
        return "{\"x\":" + System.currentTimeMillis() +", \"y\":"+ humidite +"}";
    }

}

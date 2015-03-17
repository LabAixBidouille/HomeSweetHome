package com.springapp.main;

import com.springapp.config.MainConfig;
import org.springframework.boot.SpringApplication;


public class MainApp {

    public static void main(String[] args) {
        try {
            SpringApplication.run(MainConfig.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.example.demo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DemoApp {

    private static final Logger LOGGER = Logger.getLogger(DemoApp.class.getName());

    public void runWorkload(long durationMillis) {
        LOGGER.info("Demo app is running...");
        try {
            Thread.sleep(durationMillis);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Workload interrupted", e);
            Thread.currentThread().interrupt();
        }
        LOGGER.info("Finished.");
    }

    public static void main(String[] args) {
        new DemoApp().runWorkload(2000);
    }
}
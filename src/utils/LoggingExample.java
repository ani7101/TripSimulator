package utils;

import org.slf4j.*;

public class LoggingExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingExample.class);

    private static final Logger SIMULATION_LOGGER = LoggerFactory.getLogger("simulation");

    private static final Logger IOT_API_LOGGER = LoggerFactory.getLogger("iot-api");

    private static final Logger HERE_MAPS_LOGGER = LoggerFactory.getLogger("here-maps");

    public static void main(String[] args) {
        // MDC will be used to store the user and server instance where the logging is done on.
        MDC.put("serverUrl", "https://aniragha-lite.internal.iot.ocs.oraclecloud.com/");

        LOGGER.info("This works!!");
        SIMULATION_LOGGER.info("Simulation logger implemented");
        IOT_API_LOGGER.info("Trip created");
        HERE_MAPS_LOGGER.info("Route retrieved");
    }
}

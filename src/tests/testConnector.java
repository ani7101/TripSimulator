package tests;

import device.ConnectorAPIClient;
import device.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.Generator;

public class testConnector {
    ConnectorAPIClient client;

    @Test(priority = 1)
    @Parameters({"username", "password"})
    public void testConfig(String username, String password) {
        client = new ConnectorAPIClient("https://aniragha-lite.device.internal.iot.ocs.oraclecloud.com/cgw/TripSimulatorController", username, password);
    }

    @Test(priority = 2)
    public void sendData() {
        Payload payload = new Payload();
        payload.setDeviceName("simulator-vehicle-" + "123123123" + "-sensor");
        payload.setDeviceIdentifier("obd2-sensor" + Generator.generateRandomUUID());
        payload.setMeasurementTime("2022-06-14T21:45:00.844Z");

        PayloadData payloadData = new PayloadData(
                12.972442,  // latitude
                77.580643,  // longitude
                95,         // vehicle speed
                2000,       // engine RPM
                2,          // number of DTCs (at the time)
                26.8,       // engine coolant temp
                381,        // odometer
                4.1,        // throttle position
                147,        // total fuel used
                1234,       // runtime since engine start
                187,        // mass air flow
                21,         // average fuel economy
                87          // distance since DTCs cleared
        );

        payload.setData(payloadData);

        client.postPayload(payload);
    }
}

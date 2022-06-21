package tests;

import connector.ConnectorAPIClient;
import device.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import payload.Payload;
import payload.subclasses.PayloadData;
import utils.Generator;

public class TestDeviceConnector {
    DeviceAPIClient deviceClient;
    ConnectorAPIClient connectorClient;

    @Test(priority = 1)
    @Parameters({"baseUrl", "connectorUrl", "username", "password"})
    public void testConfig(String baseUrl, String connectorUrl, String username, String password) {
        deviceClient = new DeviceAPIClient(baseUrl, username, password);
        connectorClient = new ConnectorAPIClient(connectorUrl, username, password);
    }
    @Test(priority = 2)
    public void testFetchAll() {
        System.out.println(deviceClient.getAll());
    }

    // Testing connectors
    @Test(priority = 3)
    public void sendDataUsingConnector() {
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

        connectorClient.postPayload(payload);
    }
}

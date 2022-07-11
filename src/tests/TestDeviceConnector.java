package tests;

import bulkGenerators.DeviceBulkGenerator;
import connector.ConnectorAPIClient;
import device.*;
import equipment.EquipmentDeviceGenerator;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import payload.vehicle.Payload;
import payload.vehicle.subclasses.PayloadData;
import utils.DateTime;
import utils.Generator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static bulkGenerators.DeviceBulkGenerator.bulkCreateVehicleDevices;

public class TestDeviceConnector {
    DeviceAPIClient deviceClient;
    ConnectorAPIClient connectorClient;

    @Test(priority = 1)
    @Parameters({"baseUrl", "username", "password"})
    public void testConfig(String baseUrl, String username, String password) {
        deviceClient = new DeviceAPIClient(baseUrl, username, password);
        connectorClient = new ConnectorAPIClient(username, password);
    }

    @Test(priority = 2)
    public void testFetchAll() {
        System.out.println(deviceClient.getAll());
    }

    // Testing connectors
    @Test(priority = 3)
    @Parameters({"vehicleConnectorUrl"})
    public void sendDataUsingVehicleConnector(String vehicleConnectorUrl) {
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

        System.out.println(connectorClient.postPayload(vehicleConnectorUrl, payload));
    }

    @Test(priority = 3)
    @Parameters({"equipmentControllerUrl"})
    public void sendDataUsingEquipmentConnector(String equipmentConnectorUrl) {
        String uniqueId = Generator.generateRandomUUID();

        payload.equipment.subclasses.PayloadData payloadData = new payload.equipment.subclasses.PayloadData(
                12.972442,  // latitude
                77.580643,          // longitude
                1.0,                // tilt
                2,                  // light
                35.6,               // temperature
                1.0,                // pressure
                70,                 // humidity
                1.0,                // shock
                25.6,               //ambient temperature
                1.0                 // tamper detection
        );

        payload.equipment.Payload payload = new payload.equipment.Payload(
                EquipmentDeviceGenerator.generateEquipmentDeviceName(uniqueId, "equipment"),
                EquipmentDeviceGenerator.generateEquipmentDeviceIdentifier(uniqueId, "equipment"),
                DateTime.localDateTimeToIso8601(LocalDateTime.now()),
                payloadData
        );

        System.out.println(connectorClient.postPayload(equipmentConnectorUrl, payload));
    }


    // Testing device creation
    @Test(priority = 4)
    @Parameters({"baseUrl", "vehicleConnectorUrl", "username", "password"})
    public void createVehicleDevices(String baseUrl, String vehicleConnectorUrl, String username, String password) {
        ArrayList<Device> devices = DeviceBulkGenerator.bulkCreateVehicleDevices(baseUrl, vehicleConnectorUrl, username, password, 5, Generator.generateRandomUUID(5));
    }

    @Test(priority = 4)
    @Parameters({"equipmentControllerUrl", "username", "password"})
    public void createEquipmentDevices(String equipmentConnectorUrl, String username, String password) {
        ArrayList<String> deviceNames = new ArrayList<>(5);

        for (int i = 0; i < 5; i++) {
            deviceNames.add(
                    EquipmentDeviceGenerator.createEquipmentDevice(
                            equipmentConnectorUrl, username, password, "equipment", Generator.generateRandomUUID()
                    )
            );
        }
    }
}

package bulkGenerators;

import connector.ConnectorAPIClient;
import device.Device;
import device.DeviceAPIClient;
import payload.Payload;
import payload.subclasses.PayloadData;
import utils.DateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DeviceConnectorBulkGenerator {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private DeviceConnectorBulkGenerator() {
        throw new AssertionError();
    }

    private static PayloadData dummyPayloadData = new PayloadData(
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


    public static Payload populatePayload(String uniqueId) {
        String currTime = DateTime.localDateTimeToIso8601(LocalDateTime.now());

        Payload payload = new Payload(
                "simulation-obd2-device-" + uniqueId,            // device name
                generateDeviceIdentifier(uniqueId),                         // device identifier
                currTime,                                                   // measurement time
                dummyPayloadData                                            // data
        );

        return payload;
    }

    public static Payload populatePayload(String deviceName, String deviceId) {
        String currTime = DateTime.localDateTimeToIso8601(LocalDateTime.now());

        Payload payload = new Payload(
                deviceName,         // device name
                deviceId,           // device identifier
                currTime,           // measurement time
                dummyPayloadData    // data
        );

        return payload;
    }

    public static ArrayList<Device> bulkCreateDevice(
            String baseUrl,
            String connectorUrl,
            String username,
            String password,
            int requiredDevices,
            ArrayList<String> uniqueIds
    ) {
        // Initialize the connectors
        ConnectorAPIClient connectorClient = new ConnectorAPIClient(connectorUrl, username, password);
        DeviceAPIClient deviceClient = new DeviceAPIClient(baseUrl, username, password);

        // Create devices
        for (int i = 0; i < requiredDevices; i++) {
            String response = connectorClient.postPayload(populatePayload(uniqueIds.get(i)));
            if (response != "Request accepted") {
                throw new RuntimeException(response);
            }
        }

        ArrayList<Device> devices = new ArrayList<>(requiredDevices);

        // Getting the list of devices created (to obtain their deviceId)
        for (Device device : deviceClient.getAll()) {
            for (String uniqueId : uniqueIds) {
                if (device.getHardwareId().equals(generateDeviceIdentifier(uniqueId))) {
                    devices.add(device);
                }
            }
        }

        return devices;
    }

    private static String generateDeviceIdentifier(String uniqueId) {
        return "simulation-obd2-sensor-" + uniqueId;
    }

}

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

    public static int LIMIT = 250;


    //region bulk generators

    /**
     * Creates devices through the connector and sends them back as
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param connectorUrl URL (inclusive of the complete path) to the connector. It is found in the connectors' info under the configuration options.
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     * @param requiredDevices Number of devices to be created
     * @param uniqueIds Unique UUIDs for naming the vehicles (as per NamingConvention.MD)
     * @return ArrayList(Device): List of randomly created devices
     */
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
            // TODO: 21/06/2022 Handle exceptions while creating connectors
        }

        ArrayList<Device> devices = new ArrayList<>(requiredDevices);

        ArrayList<Device> allDevices = deviceClient.getAll(LIMIT);
        // Getting the list of devices created (to obtain their deviceId)
        for (Device device : allDevices) {
            for (String uniqueId : uniqueIds) {
                if (device.getDeviceIdentifier().equals(generateDeviceIdentifier(uniqueId))) {
                    devices.add(device);
                }
            }
        }

        return devices;
    }


    //endregion
    //region Utils

    private static String generateDeviceIdentifier(String uniqueId) {
        return "simulation-obd2-sensor-" + uniqueId;
    }


    //region Payload

    private static final PayloadData dummyPayloadData = new PayloadData(
            12.972442,  // latitude
            77.580643,  // longitude
            0,         // vehicle speed
            0,       // engine RPM
            0,          // number of DTCs (at the time)
            26.8,       // engine coolant temp
            0,        // odometer
            0,        // throttle position
            0,        // total fuel used
            0,       // runtime since engine start
            0,        // mass air flow
            21,         // average fuel economy
            0          // distance since DTCs cleared
    );


    /**
     * Generates a payload with the device specifications as per NamingConvention.MD
     * @param uniqueId Unique ID for naming the device (as per NamingConvention.MD)
     * @return Payload: Generated payload with dummy data
     */
    public static Payload populatePayload(String uniqueId) {
        String currTime = DateTime.localDateTimeToIso8601(LocalDateTime.now());

        return new Payload(
                "simulation-obd2-device-" + uniqueId,            // device name
                generateDeviceIdentifier(uniqueId),                         // device identifier
                currTime,                                                   // measurement time
                dummyPayloadData                                            // data
        );
    }

    /**
     * Generates a payload with the device specifications as per NamingConvention.MD
     * @param deviceName Name of the device for which the payload is created
     * @param deviceId ID of the device for which the payload is created
     * @return Payload: Generated payload with dummy data
     */
    public static Payload populatePayload(String deviceName, String deviceId) {
        String currTime = DateTime.localDateTimeToIso8601(LocalDateTime.now());

        return new Payload(
                deviceName,         // device name
                deviceId,           // device identifier
                currTime,           // measurement time
                dummyPayloadData    // data
        );
    }

    //endregion
    //endregion

}

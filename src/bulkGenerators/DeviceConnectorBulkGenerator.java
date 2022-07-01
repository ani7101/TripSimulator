package bulkGenerators;

import connector.ConnectorAPIClient;
import device.Device;
import device.DeviceAPIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import payload.vehicle.Payload;
import payload.vehicle.subclasses.PayloadData;
import utils.DateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DeviceConnectorBulkGenerator {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private DeviceConnectorBulkGenerator() {
        throw new AssertionError();
    }

    public static int LIMIT = 500;

    private static final Logger IOT_API_LOGGER = LoggerFactory.getLogger("iot-api");


    //region bulk generators
    //---------------------------------------------------------------------------------------

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

        ArrayList<Device> devices = new ArrayList<>(requiredDevices);

        // Create devices
        for (int i = 0; i < requiredDevices; i++) {
            try {
                Payload payload = populatePayload(uniqueIds.get(i));
                String response = connectorClient.postPayload(payload);

                if (!response.equals("Request Approved")) {
                    // IOT_API_LOGGER.error("Connector is not created for {}:\n{}", payload.getDeviceIdentifier(), response);
                }

                Device device = deviceClient.getOneByIdentifier(payload.getDeviceIdentifier());
                devices.add(device);
            } catch (Exception e) {
                i--;
                IOT_API_LOGGER.warn("Trip is not created", e);
            }
        }

        return devices;
    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    private static String generateDeviceIdentifier(String uniqueId) {
        return "simulation-obd2-sensor-" + uniqueId;
    }


    //region Payload
    //---------------------------------------------------------------------------------------

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

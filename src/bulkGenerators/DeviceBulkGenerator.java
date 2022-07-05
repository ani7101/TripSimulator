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

public class DeviceBulkGenerator {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private DeviceBulkGenerator() {
        throw new AssertionError();
    }

    private static final Logger IOT_API_LOGGER = LoggerFactory.getLogger("iot-api");


    //region bulk generators
    //---------------------------------------------------------------------------------------

    /**
     * Creates devices through the connector and retrieves information about them.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param vehicleConnectorUrl URL (inclusive of the complete path) to the connector. It is found in the connectors' info under the configuration options.
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     * @param requiredDevices Number of devices to be created
     * @param uniqueIds Unique UUIDs for naming the vehicles (as per NamingConvention.MD)
     * @return ArrayList(Device): List of randomly created devices
     */
    public static ArrayList<Device> bulkCreateVehicleDevices(
            String baseUrl,
            String vehicleConnectorUrl,
            String username,
            String password,
            int requiredDevices,
            ArrayList<String> uniqueIds
    ) {
        // Initialize the connectors
        ConnectorAPIClient connectorClient = new ConnectorAPIClient(username, password);
        DeviceAPIClient deviceClient = new DeviceAPIClient(baseUrl, username, password);

        ArrayList<Device> devices = new ArrayList<>(requiredDevices);

        // Create devices
        for (int i = 0; i < requiredDevices; i++) {
            try {
                Payload vehiclePayload = populateVehiclePayload(uniqueIds.get(i));
                String response = connectorClient.postPayload(vehicleConnectorUrl, vehiclePayload);

                if (!response.equals("Request accepted")) {
                    throw new Exception(response);
                }

                Device device = deviceClient.getOneByIdentifier(vehiclePayload.getDeviceIdentifier());
                devices.add(device);
            } catch (Exception e) {
                i--;
                IOT_API_LOGGER.warn("Device is not created", e);
            }
        }

        return devices;
    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    private static String generateVehicleDeviceIdentifier(String uniqueId) {
        return "simulation-obd2-sensor-" + uniqueId;
    }


    //region Vehicle payload
    //---------------------------------------------------------------------------------------

    private static final PayloadData dummyVehiclePayloadData = new PayloadData(
            12.972442,  // latitude
            77.580643,      // longitude
            0,              // vehicle speed
            0,              // engine RPM
            0,              // number of DTCs (at the time)
            26.8,           // engine coolant temp
            0,              // odometer
            0,              // throttle position
            0,              // total fuel used
            0,              // runtime since engine start
            0,              // mass air flow
            21,             // average fuel economy
            0               // distance since DTCs cleared
    );


    /**
     * Generates a payload with the device specifications as per NamingConvention.MD
     * @param uniqueId Unique ID for naming the device (as per NamingConvention.MD)
     * @return Payload: Generated payload with dummy data
     */
    public static Payload populateVehiclePayload(String uniqueId) {
        return new Payload(
                "simulation-obd2-device-" + uniqueId,            // device name
                generateVehicleDeviceIdentifier(uniqueId),                  // device identifier
                DateTime.localDateTimeToIso8601(LocalDateTime.now()),                                                   // measurement time
                dummyVehiclePayloadData                                     // data
        );
    }

    /**
     * Generates a payload with the device specifications as per NamingConvention.MD
     * @param deviceName Name of the device for which the payload is created
     * @param deviceId ID of the device for which the payload is created
     * @return Payload: Generated payload with dummy data
     */
    public static Payload populateVehiclePayload(String deviceName, String deviceId) {
        return new Payload(
                deviceName,                 // device name
                deviceId,                   // device identifier
                DateTime.localDateTimeToIso8601(LocalDateTime.now()),                   // measurement time
                dummyVehiclePayloadData     // data
        );
    }

    //endregion

    //endregion

}

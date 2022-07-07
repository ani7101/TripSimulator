package equipment;

import connector.ConnectorAPIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import payload.equipment.Payload;
import payload.equipment.subclasses.PayloadData;
import utils.DateTime;

import java.time.LocalDateTime;

public class EquipmentDeviceGenerator {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private EquipmentDeviceGenerator() {
        throw new AssertionError();
    }

    private static final Logger IOT_API_LOGGER = LoggerFactory.getLogger("iot-api");


    //region Bulk generators
    //---------------------------------------------------------------------------------------

    /**
     * Creates devices through the connector and retrieves information about them.
     * @param equipmentConnectorUrl URL (inclusive of the complete path) to the connector. It is found in the connectors' info under the configuration options.
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     * @param uniqueId Unique UUIDs for naming the vehicles (as per NamingConvention.MD)
     * @return ArrayList(String): List of randomly created device names
     */
    public static String createEquipmentDevice(
            String equipmentConnectorUrl,
            String username,
            String password,
            String type,
            String uniqueId
    ) {
        // Initialize the connectors
        ConnectorAPIClient connectorClient = new ConnectorAPIClient(username, password);

        String deviceName = null;

            try {
                Payload equipmentPayload = populateEquipmentPayload(uniqueId, type);
                String response = connectorClient.postPayload(equipmentConnectorUrl, equipmentPayload);

                // Handling failures while creating equipment devices
                if (!response.equals("Request accepted")) {
                    throw new Exception(response);
                }

                // Updating the device name as the IoT server adds `Device {}` while creating devices via connectors
                deviceName = "Device-" + equipmentPayload.getDeviceIdentifier();

            } catch (Exception e) {
                IOT_API_LOGGER.warn("Device for equipment is not created", e);
            }

        return deviceName;
    }

    //Utils

    public static String generateEquipmentDeviceIdentifier(String uniqueId, String type) {
        return "simulator-" + type + "-sensor-" + uniqueId;
    }

    public static String generateEquipmentDeviceName(String uniqueId, String type) {
        return "Device-simulator-" + type + "-sensor-" + uniqueId;
    }

    public static String DeviceIdentifierFromDeviceName(String deviceName) {
        return deviceName.substring(deviceName.indexOf("-") + 1);
    }


    //region Equipment payload
    //---------------------------------------------------------------------------------------

    private static final PayloadData dummyEquipmentPayloadData = new PayloadData(
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

    /**
     * Generates a payload with the device specifications as per NamingConvention.MD
     * @param uniqueId Unique ID for naming the device (as per NamingConvention.MD)
     * @return Payload: Generated payload with dummy data
     */
    public static Payload populateEquipmentPayload(String uniqueId, String type) {
        return new Payload(
                generateEquipmentDeviceName(uniqueId, type),            //device name
                generateEquipmentDeviceIdentifier(uniqueId, type),      // device identifier
                DateTime.localDateTimeToIso8601(LocalDateTime.now()),   // measurement time
                dummyEquipmentPayloadData                               // data
        );
    }

    /**
     * Generates a payload with the device specifications as per NamingConvention.MD
     * @param deviceName Name of the device for which the payload is created
     * @param deviceId ID of the device for which the payload is created
     * @return Payload: Generated payload with dummy data
     */
    public static Payload populateEquipmentPayloadWithDetails(String deviceName, String deviceId) {
        return new Payload(
                deviceName,                                             //device name
                deviceId,                                               // device identifier
                DateTime.localDateTimeToIso8601(LocalDateTime.now()),   // measurement time
                dummyEquipmentPayloadData                               // data
        );

    }

    /**
     * Generates a payload with the device specifications as per NamingConvention.MD.
     * <br>
     * We use sensor instead of device to sensor to get the device identifier.
     * @param deviceName Name (according to NamingConvention.MD) for the device
     * @return Equipment payload with dummy values
     */
    public static Payload populateEquipmentFromDeviceName(String deviceName, int pickupStopSequence, int dropStopSequence) {
        Payload payload = new Payload(
                deviceName,                                             //device name
                DeviceIdentifierFromDeviceName(deviceName),             // device identifier
                DateTime.localDateTimeToIso8601(LocalDateTime.now()),   // measurement time
                dummyEquipmentPayloadData                               // data
        );
        payload.setPickupStopSequence(pickupStopSequence);
        payload.setDropStopSequence(dropStopSequence);

        return payload;
    }

    //endregion
    //endregion

}

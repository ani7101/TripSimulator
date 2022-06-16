package device;

import utils.DateTimeFormatter;
import utils.Generator;
import vehicle.VehicleDevicePayload;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DeviceConnectorWrapper {
    protected ConnectorAPIClient connectorClient;

    protected DeviceAPIClient deviceClient;

    protected int requiredInstances;

    protected ArrayList<VehicleDevicePayload> vehicleDevicePayloads = new ArrayList<VehicleDevicePayload>();

    @Deprecated
    private ArrayList<Device> devices;

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


    /**
     * Initializes the connection to the APIs and creates a random list of identifiers for the device-vehicle combination
     * @param baseUrl URL to the IoT server instance
     * @param connectorUrl URL to the (HTTP) connector API server
     * @param username Username for basic auth for the API servers
     * @param password Password for basic auth for the API servers
     */
    public DeviceConnectorWrapper(String baseUrl, String connectorUrl, String username, String password, int requiredInstances) {
        connectorClient = new ConnectorAPIClient(connectorUrl, username, password);
        deviceClient = new DeviceAPIClient(baseUrl, username, password);

        this.requiredInstances = requiredInstances;


        for (int i = 0; i < requiredInstances; i++) {
            vehicleDevicePayloads.add(new VehicleDevicePayload());
            vehicleDevicePayloads.get(i).setUniqueId(Generator.generateRandomUUID());
        }
    }

    public void populatePayload() {
        String currTime = DateTimeFormatter.localDateTimeToIso8601(LocalDateTime.now());

        for (int i = 0; i < requiredInstances; i++) {
            Payload tempPayload = new Payload(
                    "simulation-obd2-device-" + vehicleDevicePayloads.get(i).getUniqueId(),   // device name
                    "simulation-obd2-sensor-" + vehicleDevicePayloads.get(i).getUniqueId(),              // device identifier
                    currTime,                                                   // measurement time
                    dummyPayloadData                                            // data
            );

            vehicleDevicePayloads.get(i).setPayload(tempPayload);
        }
    }

    public void createDeviceUsingConnector() {
        for (int i = 0; i < requiredInstances; i++) {
            String response = connectorClient.postPayload(vehicleDevicePayloads.get(i).getPayload());
            if (response != "Request Accepted") {
                // TODO: 16/06/2022 Replace with proper logger logic
                throw new RuntimeException(response);
            }
        }
        // Getting the device information (ID)
        ArrayList<Device> devices = deviceClient.getAll();
        for (int i = 0; i < requiredInstances; i++) {
            vehicleDevicePayloads.get(i).setDevice(
                    searchDeviceId(vehicleDevicePayloads.get(i).getPayload().getDeviceIdentifier(),
                    devices));
        }
    }

    private Device searchDeviceId(String deviceIdentifier, ArrayList<Device> devices) {
        for (Device device : devices) {
            if (device.getHardwareId() == deviceIdentifier) {
                return device;
            }
        }

        return null;
    }

    @Deprecated
    public Device searchDeviceId(String deviceIdentifier) {
        if (devices != null) {
            for (Device device : devices) {
                if (device.getHardwareId() == deviceIdentifier) {
                    return device;
                }
            }
        }

        // In case it isn't in the offline devices repository
        ArrayList<Device> devices = deviceClient.getAll();

        for (Device device : devices) {
            if (deviceIdentifier.equals(device.getHardwareId())) { // device identifier & hardware id are equivalent
                return device;
            }
        }

        return null;
    }



    @Deprecated
    public void updateDevices() {
        devices = deviceClient.getAll();
    }


    public ArrayList<Device> getDevicesFromAPI() {
        return deviceClient.getAll();
    }


}

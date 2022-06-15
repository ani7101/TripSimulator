package device;

import utils.Generator;

import java.util.ArrayList;

public class DeviceConnectorWrapper {
    protected ConnectorAPIClient connectorClient;

    protected DeviceAPIClient deviceClient;

    protected int required;

    protected ArrayList<String> deviceIdentifiers; // Equivalent to hardwareIds

    protected ArrayList<Payload> connectorPayloads = new ArrayList<>();

    protected ArrayList<Device> devices;

    protected PayloadData dummyPayload = new PayloadData(
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


    public DeviceConnectorWrapper(String baseUrl, String connectorUrl, String username, String password, ArrayList<String> deviceIdentifiers) {
        connectorClient = new ConnectorAPIClient(connectorUrl, username, password);
        deviceClient = new DeviceAPIClient(baseUrl, username, password);

        this.deviceIdentifiers = deviceIdentifiers;
        this.required = deviceIdentifiers.size();
    }

    public DeviceConnectorWrapper(String baseUrl, String connectorUrl, String username, String password, int required) {
        connectorClient = new ConnectorAPIClient(connectorUrl, username, password);
        deviceClient = new DeviceAPIClient(baseUrl, username, password);

        this.required = required;

        deviceIdentifiers = new ArrayList<String>(required);
        for (int i = 0; i < required; i++) {
            deviceIdentifiers.add("obd2-sensor" + Generator.generateRandomUUID());
        }
    }

    public void populatePayload() {

        for (int i = 0; i < required; i++) {
            String deviceIdentifier = deviceIdentifiers.get(i);

            connectorPayloads.add(new Payload(
                    "simulator-vehicle-" + deviceIdentifier + "-sensor", // device name
                    deviceIdentifier,                                               // device identifier
                    "2022-06-14T21:45:00.844Z",                                     // measurement time
                    dummyPayload                                                    // data
            ));
        }
    }

    public void createDeviceUsingConnector() {
        // TODO: 15/06/2022 Have to handle errors in case a connector doesn't approve the request
        for (Payload payload : connectorPayloads) {
            connectorClient.postPayload(payload);
        }
        devices = deviceClient.getAll();
    }

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

    public ArrayList<String> getDeviceIdentifiers() {
        return deviceIdentifiers;
    }

    public void updateDevices() {
        devices = deviceClient.getAll();
    }

    public ArrayList<Device> getDevices() {
        return deviceClient.getAll();
    }
}

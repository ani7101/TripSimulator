package vehicle;

import device.DeviceConnectorWrapper;

import java.util.ArrayList;

public class VehicleDeviceWrapper extends DeviceConnectorWrapper{
    protected ArrayList<PopulateVehicle> vehicles = new ArrayList<PopulateVehicle>();

    public VehicleDeviceWrapper(String baseUrl, String connectorUrl, String username, String password, int required) {
        super(baseUrl, connectorUrl, username, password, required);

        populatePayload();
        createDeviceUsingConnector();

        for (String deviceIdentifier : deviceIdentifiers) {
            vehicles.add(new PopulateVehicle(baseUrl, username, password, searchDeviceId(deviceIdentifier).getId()));
        }

        for (PopulateVehicle vehicle : vehicles) {
            vehicle.sendQuery();
        }

    }

    public ArrayList<Vehicle> getVehicles() {
        ArrayList<Vehicle> vehicleArrayList = new ArrayList<Vehicle>();

        for (PopulateVehicle vehicle : vehicles) {
            vehicleArrayList.add(vehicle.getVehicle());
        }

        return vehicleArrayList;
    }
}

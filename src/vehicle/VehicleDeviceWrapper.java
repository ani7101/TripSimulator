package vehicle;

import device.DeviceConnectorWrapper;

import java.util.ArrayList;


public class VehicleDeviceWrapper extends DeviceConnectorWrapper{

    @Deprecated
    protected ArrayList<GeneratedOBD2Vehicle> vehicles;

    public VehicleDeviceWrapper(String baseUrl, String connectorUrl, String username, String password, int requiredInstances) {
        super(baseUrl, connectorUrl, username, password, requiredInstances);

        populatePayload();
        createDeviceUsingConnector();

        for (int i = 0; i < requiredInstances; i++) {
            GeneratedOBD2Vehicle tempVehicle = new GeneratedOBD2Vehicle(baseUrl, username, password, vehicleDevicePayloads.get(i).getDevice().getId());

            tempVehicle.sendQuery();

            vehicleDevicePayloads.get(i).setGeneratedOBD2Vehicle(tempVehicle);
        }

    }

    public ArrayList<Vehicle> getVehicles() {
        ArrayList<Vehicle> vehicleArrayList = new ArrayList<Vehicle>();

        for (VehicleDevicePayload vehicleDevicePayload : vehicleDevicePayloads) {
            vehicleArrayList.add(vehicleDevicePayload.getGeneratedOBD2Vehicle().getVehicle());
        }

        return vehicleArrayList;
    }
    public Vehicle getVehicle(String vehicleName) {
        for (VehicleDevicePayload vehicleDevicePayload : vehicleDevicePayloads) {
            Vehicle vehicle = vehicleDevicePayload.getGeneratedOBD2Vehicle().getVehicle();
            if (vehicle.getName().equals(vehicleName)) {
                return vehicle;
            }
        }
        return null;
    }

    public ArrayList<VehicleDevicePayload> getVehicleDevicePayloads() {
        return vehicleDevicePayloads;
    }
}

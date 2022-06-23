package bulkGenerators;

import device.Device;
import utils.Generator;
import vehicle.OBD2VehicleGenerator;
import vehicle.Vehicle;
import vehicle.VehicleAPIClient;
import vehicleType.OBD2VehicleTypeGenerator;
import vehicleType.VehicleType;
import vehicleType.VehicleTypeAPIClient;

import java.util.ArrayList;


public class VehicleBulkGenerator {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private VehicleBulkGenerator() {
        throw new AssertionError();
    }

    public static ArrayList<Vehicle> bulkCreateVehicles(
            String baseUrl,
            String connectorUrl,
            String username,
            String password,
            ArrayList<String> uniqueIds,
            int requiredVehicles
    ) {
        // Initialise the API clients
        VehicleAPIClient vehicleClient = new VehicleAPIClient(baseUrl, username, password);
        VehicleTypeAPIClient typeClient = new VehicleTypeAPIClient(baseUrl, username, password);

        // Creating devices
        ArrayList<Device> devices = DeviceConnectorBulkGenerator.bulkCreateDevice(baseUrl, connectorUrl, username, password, requiredVehicles, uniqueIds);

        // Creating a vehicle type based on the OBD2 data model
        VehicleType type = typeClient.create(OBD2VehicleTypeGenerator.randomizedType(baseUrl, username, password));

        ArrayList<Vehicle> vehicles = new ArrayList<>(requiredVehicles);

        // Creating the vehicles
        for (int i = 0; i < requiredVehicles; i++) {
            Device device = devices.get(i);

            Vehicle vehicle = vehicleClient.create(
              OBD2VehicleGenerator.randomizedVehicle(baseUrl, username, password, type.getId(), device.getId(), uniqueIds.get(i))
            );

            // Adding the device details to the vehicle
            vehicle.setDeviceId(device.getId());
            vehicle.setDeviceName(device.getName());

            vehicles.add(vehicle);
        }

        return vehicles;
    }

    public static ArrayList<Vehicle> bulkCreateVehicles(
            String baseUrl,
            String connectorUrl,
            String username,
            String password,
            String vehicleTypeId,
            ArrayList<String> uniqueIds,
            int requiredVehicles
    ) {
        // Initialise the API clients
        VehicleAPIClient vehicleClient = new VehicleAPIClient(baseUrl, username, password);

        // Creating devices
        ArrayList<Device> devices = DeviceConnectorBulkGenerator.bulkCreateDevice(baseUrl, connectorUrl, username, password, requiredVehicles, uniqueIds);


        ArrayList<Vehicle> vehicles = new ArrayList<>(requiredVehicles);

        // Creating the vehicles
        for (int i = 0; i < requiredVehicles; i++) {
            Device device = devices.get(i);


            Vehicle vehicle = vehicleClient.create(
                    OBD2VehicleGenerator.randomizedVehicle(baseUrl, username, password, vehicleTypeId, device.getId(), uniqueIds.get(i))
            );
            // Adding the device details to the vehicle
            vehicle.setDeviceId(device.getId());
            vehicle.setDeviceName(device.getName());

            vehicles.add(vehicle);
        }

        return vehicles;
    }
}

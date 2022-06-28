package bulkGenerators;

import device.Device;

import vehicle.OBD2VehicleGenerator;
import vehicle.Vehicle;
import vehicle.VehicleAPIClient;
import vehicleType.OBD2VehicleTypeGenerator;
import vehicleType.VehicleType;
import vehicleType.VehicleTypeAPIClient;

import java.util.ArrayList;

/**
 * Contains two static methods to create multiple vehicles from scratch as well as use an input vehicle type to create a vehicle.
 */
public class VehicleBulkGenerator {

    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private VehicleBulkGenerator() {
        throw new AssertionError();
    }

    //region Bulk generators
    //---------------------------------------------------------------------------------------

    /**
     * Creates multiple vehicles on the IoT server instance.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param connectorUrl URL (inclusive of the complete path) to the connector. It is found in the connectors' info under the configuration options.
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     * @param uniqueIds Unique UUIDs for naming the vehicles (as per NamingConvention.MD)
     * @param requiredVehicles Number of vehicles to be created
     * @return ArrayList(Vehicle): List of the randomly created vehicles
     */
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
        VehicleType type = typeClient.create(OBD2VehicleTypeGenerator.randomizedType());

        ArrayList<Vehicle> vehicles = new ArrayList<>(requiredVehicles);

        // Creating the vehicles
        for (int i = 0; i < requiredVehicles; i++) {
            Device device = devices.get(i);

            Vehicle vehicle = vehicleClient.create(
              OBD2VehicleGenerator.randomizedVehicleFromVehicleType(type.getId(), device.getId(), uniqueIds.get(i))
            );

            // Adding the device details to the vehicle
            vehicle.setDeviceName(device.getName());
            vehicle.setDeviceIdentifier(device.getIdentifier());

            vehicles.add(vehicle);
        }

        return vehicles;
    }


    /**
     * Creates multiple vehicles on the IoT server instance.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param connectorUrl URL (inclusive of the complete path) to the connector. It is found in the connectors' info under the configuration options.
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     * @param vehicleTypeId vehicle type to be which the vehicles belong
     * @param uniqueIds Unique UUIDs for naming the vehicles (as per NamingConvention.MD)
     * @param requiredVehicles Number of vehicles to be made
     * @return ArrayList(Vehicle): List of the randomly created vehicles
     */
    public static ArrayList<Vehicle> bulkCreateVehiclesFromVehicleType(
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
                    OBD2VehicleGenerator.randomizedVehicleFromVehicleType(vehicleTypeId, device.getId(), uniqueIds.get(i))
            );
            // Adding the device details to the vehicle
            vehicle.setDeviceName(device.getName());
            vehicle.setDeviceIdentifier(device.getIdentifier());

            vehicles.add(vehicle);
        }

        return vehicles;
    }

    //endregion

}

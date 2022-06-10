package vehicle;

import utils.Generator;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

// TODO: 09/06/2022  To add attribute and linking to a device for each vehicle later on (not implemented as
public class PopulateVehicle {

    private VehicleAPIClient client;

    private Vehicle vehicle;

    public PopulateVehicle(String baseUrl, String username, String password, Vehicle vehicle) {
        client = new VehicleAPIClient(baseUrl, username, password);
        this.vehicle = vehicle;
    }

    public PopulateVehicle(String baseUrl, String username, String password, String vehicleType, String make, String model) {
        client = new VehicleAPIClient(baseUrl, username, password);

        vehicle = new Vehicle(
                "SimulatorVehicle-" + Generator.generateRandomUUID(),  // name
                model,                                                        // Vehicle model
                make,                                                         // Vehicle make
                vehicleType,                                                  // ID of the vehicle type
                Generator.generateRandomString(8),                    // Registration number
                Generator.generateRandomString(6),                    // VIN
                LocalDateTime.now().getYear()                                 // Vehicle launch year
        );

        // Vehicle description
        vehicle.setDescription(vehicle.getName() + " for simlution of trips!");
    }

    public PopulateVehicle(String baseUrl, String username, String password, String vehicleType) {
        this(baseUrl, username, password, vehicleType, "defaultMake", "defaultModel");
    }

    public Vehicle sendQuery() throws ExecutionException, JsonProcessingException, InterruptedException, TimeoutException {
        return client.create(vehicle);
    }

    public Vehicle getVehicle() { return vehicle; }
}

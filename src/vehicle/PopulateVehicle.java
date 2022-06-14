package vehicle;

import utils.Generator;
import vehicleType.PopulateOBD2VehicleType;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PopulateVehicle {

    private final VehicleAPIClient client;

    private Vehicle vehicle;

    private PopulateOBD2VehicleType populateType;

    private String deviceId;

    public static final String deviceModel = "urn:com:oracle:iot:device:obd2";

    public PopulateVehicle(String baseUrl, String username, String password, Vehicle vehicle) {
        client = new VehicleAPIClient(baseUrl, username, password);
        this.vehicle = vehicle;
    }

    public PopulateVehicle(String baseUrl, String username, String password, String vehicleType, String make, String model,String deviceId) {
        client = new VehicleAPIClient(baseUrl, username, password);

        this.deviceId = deviceId;

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
        vehicle.setAttributes(addOBD2Attributes());
    }

    public PopulateVehicle(String baseUrl, String username, String password, String vehicleType, String deviceId) {
        this(baseUrl, username, password, vehicleType, "defaultMake", "defaultModel", deviceId);
    }

    public PopulateVehicle(String baseUrl, String username, String password, String deviceId) {
        client = new VehicleAPIClient(baseUrl, username, password);

        this.deviceId = deviceId;

        populateType = new PopulateOBD2VehicleType(baseUrl, username, password);
        populateType.sendQuery();

        vehicle = new Vehicle(
                "SimulatorVehicle-" + Generator.generateRandomUUID(),    // name
                "defaultModel",                                                // Vehicle model
                "defaultMake",                                                 // Vehicle make
                populateType.getType().getId(),                                // ID of the vehicle type
                Generator.generateRandomString(8),                      // Registration number
                Generator.generateRandomString(6),                      // VIN
                LocalDateTime.now().getYear()                                  // Vehicle launch year
        );

        // Vehicle description
        vehicle.setDescription(vehicle.getName() + " for simulation of trips!");
        vehicle.setAttributes(addOBD2Attributes());
    }

    private ArrayList<VehicleAttributeMinimal> addOBD2Attributes() {
        ArrayList<VehicleAttributeMinimal> attributes = new ArrayList<VehicleAttributeMinimal>();

        /* Location Parameter definition */

        attributes.add(new VehicleAttributeMinimal(
                "Latitude",
                "NUMBER",
                deviceId,
                deviceModel,
                "ora_latitude"
        ));

        attributes.add(new VehicleAttributeMinimal(
                "Longitude",
                "NUMBER",
                deviceId,
                deviceModel,
                "ora_longitude"
        ));

        /* OBD2 Parameter definition */

        attributes.add(new VehicleAttributeMinimal(
                "Speed",
                "NUMBER",
                deviceId,
                deviceModel,
                "ora_obd2_vehicle_speed"
        ));

        attributes.add(new VehicleAttributeMinimal(
                "EngineRPM",
                "NUMBER",
                deviceId,
                deviceModel,
                "ora_obd2_engine_rpm"
        ));

        attributes.add(new VehicleAttributeMinimal(
                "NumberOfDTCS",
                "NUMBER",
                deviceId,
                deviceModel,
                "ora_obd2_number_of_dtcs"
        ));

        attributes.add(new VehicleAttributeMinimal(
                "EngineCoolantTemp",
                "NUMBER",
                deviceId,
                deviceModel,
                "ora_obd2_engine_coolant_temperature"
        ));

        attributes.add(new VehicleAttributeMinimal(
                "Odometer",
                "NUMBER",
                deviceId,
                deviceModel,
                "ora_obd2_true_odometer"
        ));

        attributes.add(new VehicleAttributeMinimal(
                "ThrottlePosition",
                "NUMBER",
                deviceId,
                deviceModel,
                "ora_obd2_throttle_position"
        ));

        attributes.add(new VehicleAttributeMinimal(
                "TotalFuelUsed",
                "NUMBER",
                deviceId,
                deviceModel,
                "ora_obd2_total_fuel_used"
        ));

        attributes.add(new VehicleAttributeMinimal(
                "RuntimeSinceEngineStart",
                "NUMBER",
                deviceId,
                deviceModel,
                "ora_obd2_runtime_since_engine_start"
        ));

        attributes.add(new VehicleAttributeMinimal(
                "MassAirFlow",
                "NUMBER",
                deviceId,
                deviceModel,
                "ora_obd2_mass_air_flow"
        ));

        attributes.add(new VehicleAttributeMinimal(
                "AverageFuelEconomy",
                "NUMBER",
                deviceId,
                deviceModel,
                "ora_obd2_average_fuel_economy"
        ));

        attributes.add(new VehicleAttributeMinimal(
                "DistanceSinceDTCSCleared",
                "NUMBER",
                deviceId,
                deviceModel,
                "ora_obd2_distance_since_dtcs_cleared"
        ));

        return attributes;
    }

    public Vehicle sendQuery() {
        Vehicle response = client.create(vehicle);
        vehicle.setId(response.getId());
        return response;
    }

    public Vehicle getVehicle() { return vehicle; }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getDeviceId() { return deviceId; }
}

package vehicle;

import utils.Generator;
import vehicle.subclasses.VehicleAttributeMinimal;
import vehicleType.VehicleType;
import vehicleType.OBD2VehicleTypeGenerator;
import vehicleType.VehicleTypeAPIClient;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class OBD2VehicleGenerator {
    public static final String deviceModel = "urn:com:oracle:iot:device:obd2";

    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private OBD2VehicleGenerator() {
        throw new AssertionError();
    }

    public static Vehicle randomizedVehicle(
            String baseUrl,
            String username,
            String password,
            String deviceId
    ) {
        // Creating a new vehicle type and posting it to the IoT server
        VehicleTypeAPIClient typeClient = new VehicleTypeAPIClient(baseUrl, username, password);
        VehicleType vehicleType = OBD2VehicleTypeGenerator.randomizedType(baseUrl, username, password);
        typeClient.create(vehicleType);

        return randomizedVehicle(baseUrl, username, password, vehicleType.getId(), deviceId, "defaultModel","defaultMake");
    }

    public static Vehicle randomizedVehicle(
            String baseUrl,
            String username,
            String password,
            String vehicleTypeId,
            String deviceId
    ) {
        return randomizedVehicle(baseUrl, username, password, vehicleTypeId, deviceId, "defaultModel","defaultMake");
    }

    public static Vehicle randomizedVehicle(
            String baseUrl,
            String username,
            String password,
            String vehicleTypeId,
            String deviceId,
            String make,
            String model
    ) {
        Vehicle vehicle = new Vehicle(
                "simulator-vehicle-" + deviceId,         // name
                model,                                         // Vehicle model
                make,                                          // Vehicle make
                vehicleTypeId,                                 // ID of the vehicle type
                Generator.generateRandomString(8),      // Registration number
                Generator.generateRandomString(6),      // VIN
                LocalDateTime.now().getYear()                  // Vehicle launch year
        );

        vehicle.setDescription("Trip simulation vehicle linked with device " + deviceId);
        vehicle.setAttributes(getOBD2Attributes(deviceId));

        return vehicle;
    }

    private static ArrayList<VehicleAttributeMinimal> getOBD2Attributes(String deviceId) {
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
}

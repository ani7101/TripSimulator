package vehicle;

import utils.Generator;
import vehicle.subclasses.VehicleAttributeMinimal;
import vehicleType.VehicleType;
import vehicleType.OBD2VehicleTypeGenerator;
import vehicleType.VehicleTypeAPIClient;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Contains static methods to generate randomized vehicles linked to a device.
 * Vehicles can be linked to a vehicle type directly or can create a new vehicle type and link the vehicle to it.
 * <br>
 * <br>
 * Static methods:
 * <ul>
 *     <li><b>randomizedVehicle</b> - Generates a randomized vehicle with a freshly created vehicle type</li>
 *     <li><b>randomizedVehicleFromVehicleType</b> - Generates a randomized vehicle with input vehicle type</li>
 *     <li><b>randomizedVehicleFromVehicleType (with make & model)</b> - Generates a randomized vehicle with input vehicle type along with input make and input model</li>
 * </ul>
 */
public class OBD2VehicleGenerator {
    public static final String deviceModel = "urn:com:oracle:iot:device:obd2";

    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private OBD2VehicleGenerator() {
        throw new AssertionError();
    }

    //region Randomized generators
    //---------------------------------------------------------------------------------------


    /**
     * Generates a vehicle with a completely new vehicleType and linked to the input device.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     * @param deviceId ID of the device to be linked to the vehicle
     * @param uniqueId unique UUID as per the naming convention followed (refer to NamingConvention.MD)
     * @return Vehicle: randomly generated vehicle
     */
    public static Vehicle randomizedVehicle(
            String baseUrl,
            String username,
            String password,
            String deviceId,
            String uniqueId
    ) {
        // Creating a new vehicle type and posting it to the IoT server
        VehicleTypeAPIClient typeClient = new VehicleTypeAPIClient(baseUrl, username, password);
        VehicleType vehicleType = OBD2VehicleTypeGenerator.randomizedType();
        typeClient.create(vehicleType);

        return randomizedVehicleFromVehicleType(vehicleType.getId(), deviceId, uniqueId, "defaultModel","defaultMake");
    }


    /**
     * Generates a vehicle of the inputted vehicleType and linked to the input device.
     * @param vehicleTypeId vehicle type to create a vehicle in
     * @param deviceId ID of the device to be linked to the vehicle
     * @param uniqueId unique UUID as per the naming convention followed (refer to NamingConvention.MD)
     * @return Vehicle: randomly generated vehicle
     */
    public static Vehicle randomizedVehicleFromVehicleType(
            String vehicleTypeId,
            String deviceId,
            String uniqueId
    ) {
        return randomizedVehicleFromVehicleType(vehicleTypeId, deviceId, uniqueId, "defaultModel","defaultMake");
    }


    /**
     * Generates a vehicle of the inputted vehicleType and linked to the input device.
     * @param vehicleTypeId vehicle type to create a vehicle in
     * @param deviceId ID of the device to be linked to the vehicle
     * @param uniqueId unique UUID as per the naming convention followed (refer to NamingConvention.MD)
     * @param make Vehicle make
     * @param model Vehicle model
     * @return Vehicle: randomly generated vehicle
     */
    public static Vehicle randomizedVehicleFromVehicleType(
            String vehicleTypeId,
            String deviceId,
            String uniqueId,
            String make,
            String model
    ) {
        Vehicle vehicle = new Vehicle(
                "simulator-vehicle-" + uniqueId,         // name
                model,                                         // Vehicle model
                make,                                          // Vehicle make
                vehicleTypeId,                                 // ID of the vehicle type
                Generator.generateRandomString(8),      // Registration number
                Generator.generateRandomString(6),      // VIN
                LocalDateTime.now().getYear()                  // Vehicle launch year
        );

        vehicle.setDescription("Trip simulation vehicle linked with device " + uniqueId);
        vehicle.setAttributes(getOBD2Attributes(deviceId));

        return vehicle;
    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    /**
     * Generates the vehicle attribute for OBD2 parameters with the input device ID
     * @param deviceId device ID to be linked to the vehicle attribute
     * @return ArrayList(VehicleAttributeMinimal): List of OBD2 vehicle attributes
     */
    private static ArrayList<VehicleAttributeMinimal> getOBD2Attributes(String deviceId) {
        ArrayList<VehicleAttributeMinimal> attributes = new ArrayList<>();

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

    //endregion

}

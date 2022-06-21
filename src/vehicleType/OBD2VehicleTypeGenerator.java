package vehicleType;

import vehicleType.subclasses.VehicleAttribute;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class OBD2VehicleTypeGenerator {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private OBD2VehicleTypeGenerator() {
        throw new AssertionError();
    }

    public static VehicleType randomizedType(
            String baseUrl,
            String username,
            String password
    ) {
        // Random value for the name field
        String name = "TestOBD2VehicleType" + ThreadLocalRandom.current().nextInt(0, 100 + 1);

        // Values for year field
        ArrayList<Integer> years = new ArrayList<Integer>();

        int currYear = LocalDateTime.now().getYear();
        years.add(currYear);
        years.add(currYear + 1);
        years.add(currYear + 2);

        VehicleType type = new VehicleType(name, years);

        // Value for the description field
        type.setDescription("Type for the simulation of a trip, vehicle type name: " + name);

        type.setAttributes(getOBD2Attributes());

        return type;
    }

    private static ArrayList<VehicleAttribute> getOBD2Attributes() {
        ArrayList<VehicleAttribute> attributes = new ArrayList<VehicleAttribute>();

        /* Location Parameter definition */

        // Latitude Field
        attributes.add(new VehicleAttribute(
                "Latitude",
                "NUMBER",
                "urn:com:oracle:iot:device:obd2",
                true,
                false,
                "ora_latitude",
                "SENSOR"
        ));

        // Longitude Field
        attributes.add(new VehicleAttribute(
                "Longitude",
                "NUMBER",
                "urn:com:oracle:iot:device:obd2",
                true,
                false,
                "ora_longitude",
                "SENSOR"
        ));

        /* OBD2 Parameter definition */

        // Speed Field
        attributes.add(new VehicleAttribute(
                "Speed",
                "NUMBER",
                "urn:com:oracle:iot:device:obd2",
                false,
                false,
                "ora_obd2_vehicle_speed",
                "SENSOR"
        ));

        // Engine RPM Field
        attributes.add(new VehicleAttribute(
                "EngineRPM",
                "NUMBER",
                "urn:com:oracle:iot:device:obd2",
                false,
                false,
                "ora_obd2_engine_rpm",
                "SENSOR"
        ));

        // Number of DTCS Field
        attributes.add(new VehicleAttribute(
                "NumberOfDTCS",
                "NUMBER",
                "urn:com:oracle:iot:device:obd2",
                false,
                false,
                "ora_obd2_number_of_dtcs",
                "SENSOR"
        ));

        // Engine coolant temperature Field
        attributes.add(new VehicleAttribute(
                "EngineCoolantTemp",
                "NUMBER",
                "urn:com:oracle:iot:device:obd2",
                false,
                false,
                "ora_obd2_engine_coolant_temperature",
                "SENSOR"
        ));

        // Odometer Field
        attributes.add(new VehicleAttribute(
                "Odometer",
                "NUMBER",
                "urn:com:oracle:iot:device:obd2",
                false,
                false,
                "ora_obd2_true_odometer",
                "SENSOR"
        ));

        // Throttle position Field
        attributes.add(new VehicleAttribute(
                "ThrottlePosition",
                "NUMBER",
                "urn:com:oracle:iot:device:obd2",
                false,
                false,
                "ora_obd2_throttle_position",
                "SENSOR"
        ));

        // Total fuel used Field
        attributes.add(new VehicleAttribute(
                "TotalFuelUsed",
                "NUMBER",
                "urn:com:oracle:iot:device:obd2",
                false,
                false,
                "ora_obd2_total_fuel_used",
                "SENSOR"
        ));

        // Runtime since engine start Field
        attributes.add(new VehicleAttribute(
                "RuntimeSinceEngineStart",
                "NUMBER",
                "urn:com:oracle:iot:device:obd2",
                false,
                false,
                "ora_obd2_runtime_since_engine_start",
                "SENSOR"
        ));

        // Mass air flow Field
        attributes.add(new VehicleAttribute(
                "MassAirFlow",
                "NUMBER",
                "urn:com:oracle:iot:device:obd2",
                false,
                false,
                "ora_obd2_mass_air_flow",
                "SENSOR"
        ));

        // Average fuel economy Field
        attributes.add(new VehicleAttribute(
                "AverageFuelEconomy",
                "NUMBER",
                "urn:com:oracle:iot:device:obd2",
                false,
                false,
                "ora_obd2_average_fuel_economy",
                "SENSOR"
        ));

        // Distance since DTCS cleared Field
        attributes.add(new VehicleAttribute(
                "DistanceSinceDTCSCleared",
                "NUMBER",
                "urn:com:oracle:iot:device:obd2",
                false,
                false,
                "ora_obd2_distance_since_dtcs_cleared",
                "SENSOR"
        ));

        return attributes;
    }
}
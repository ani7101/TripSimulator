package vehicleType;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

/**
 * This is a single run-time class that creates a vehicle type for the OBD2 for the simulations to run and
 * stores the ID and name to the vehicle type created. This can also be ignored if one is already created and
 * can directly specify the vehicle type in it's constructor.
 */
public class PopulateOBD2VehicleType {

    private VehicleTypeAPIClient client;
    
    private VehicleType type;

    public PopulateOBD2VehicleType(String baseUrl, String username, String password, VehicleType type) {
        client = new VehicleTypeAPIClient(baseUrl, username, password);
        this.type = type;
    }


    public PopulateOBD2VehicleType(String baseUrl, String username, String password) {
        client = new VehicleTypeAPIClient(baseUrl, username,  password);

        // Random value for the name field
        String name = "TestOBD2VehicleType" + ThreadLocalRandom.current().nextInt(0, 100 + 1);

        // Values for year field
        ArrayList<Integer> years = new ArrayList<Integer>();

        int currYear = LocalDateTime.now().getYear();
        years.add(currYear);
        years.add(currYear + 1);
        years.add(currYear + 2);

        type = new VehicleType(name, years);

        // Value for the description field
        type.setDescription("Type for the simulation of a trip, vehicle type name: " + name);

        type.setAttributes(addOBD2Attributes());
    }

    private ArrayList<VehicleAttribute> addOBD2Attributes() {
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
                "EngingRPM",
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

    public VehicleType getType() {
        return type;
    }

    public ArrayList<VehicleAttribute> getOBD2Attributes() { return type.getAttributes(); }

    public VehicleType sendQuery() {
        return client.create(type);
    }
}

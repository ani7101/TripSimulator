package payload.vehicle;

import payload.vehicle.subclasses.PayloadData;

import java.util.Random;

/**
 * Generates randomized values for the payload as per the below-mentioned scheme.
 * <br>
 * <b>Random values between ranges</b> - engine RPM, engine coolant temperature, mass air flow (as of now), throttle position
 * <br>
 * <b>Values dependent on the simulation</b> - Speed, Odometer, Runtime since engine start, total fuel used, average fuel economy
 * <br>
 * <br>
 * <b>References</b>
 * <ul>
 *     <li>
 *         Mass air flow linearly varies with engine rpm above 1000 RPM (<a href="https://www.researchgate.net/figure/Flow-rate-vs-rpm-in-higher-rpms-for-a-normal-sensor_fig5_280884222">Reference</a>)
 *     </li>
 *     <li>
 *         Throttle position approximately varies linearly with rpm with a slope of 634. (<a href="https://www.researchgate.net/figure/Position-of-the-throttle-valve-versus-engine-speed-at-idle-From-Figure-7-we-can-deduct_fig1_317158492">reference</a>)
 *     </li>
 * </ul>
 */
public class PayloadDataGenerator {

    //region Constant values
    //---------------------------------------------------------------------------------------

    public static int MIN_ENGINE_RPM = 1000;
    public static int MAX_ENGINE_RPM = 10000;

    public static double MIN_COOLANT_TEMP = 30; // In celsius
    public static double MAX_COOLANT_TEMP = 55;


    // Indicated in percentages
    public static double MIN_THROTTLE_POSITION = 25;
    public static double MAX_THROTTLE_POSITION = 100;

    public static double DTC_PROBABILITY = 0.05;


    //endregion
    //region Randomized generators
    //---------------------------------------------------------------------------------------

    public static int generateEngineRPM() {
        return (int) generateRandomNumber(MIN_ENGINE_RPM, MAX_ENGINE_RPM);
    }

    public static double generateEngineCoolantTemp() {
        return generateRandomNumber(MIN_COOLANT_TEMP, MAX_COOLANT_TEMP);
    }

    public static double generateMassAirFlow(int engineRPM) {
        if (engineRPM < 500) {
            return generateRandomNumber(0, 1);
        }
        if (engineRPM < 1000) {
            return 0.005 * engineRPM;
        }
        else {
            return 0.01 * engineRPM;
        }
    }

    public static double generateMassAirFlow() {
        return generateRandomNumber(5, 10);
    }

    public static double generateThrottlePosition(int engineRPM) {
        return engineRPM * 1.0 / MAX_ENGINE_RPM;
    }

    public static double generateThrottlePosition() {
        return generateRandomNumber(MIN_THROTTLE_POSITION, MAX_THROTTLE_POSITION);
    }

    public static boolean isDTC() {
        return new Random().nextDouble() < DTC_PROBABILITY;
    }


    public static boolean isDTCFixed(int distanceTravelled) {
        // We can use (1 - negative exponential function) with a constant to check the probability that the DTC is resolved on that day
        // Although this is hard, it's quite hard to compute and considering it's done at every iteration, it's quite a bad thing

        return distanceTravelled > 3;
    }

    public static PayloadData getRandomPayloadData() {
        // Excluding code based values, rest are randomly generated
        // Code based values include latitude, longitude, vehicle speed, true odometer, runtime since engine start, total fuel used
        // Payload data also includes average fuel economy which will be treated as a constant instead of varying based on MAF, throttle position & engine RPM.

        PayloadData data = new PayloadData();

        // Independent data values
        int engineRPM = generateEngineRPM();
        data.setEngineRPM(engineRPM);
        data.setEngineCoolantTemperature(generateEngineCoolantTemp());

        data.setThrottlePosition(generateThrottlePosition(engineRPM));
        data.setMassAirFlow(generateMassAirFlow(engineRPM));

        return data;
    }

    public static Payload getRandomPayloadData(Payload payload) {
        // Excluding code based values, rest are randomly generated
        // Code based values include latitude, longitude, vehicle speed, true odometer, runtime since engine start, total fuel used
        // Payload data also includes average fuel economy which will be treated as a constant instead of varying based on MAF, throttle position & engine RPM.

        // Independent data values
        int engineRPM = generateEngineRPM();
        payload.setEngineRPM(engineRPM);
        payload.setEngineCoolantTemperature(generateEngineCoolantTemp());

        payload.setThrottlePosition(generateThrottlePosition(engineRPM));
        payload.setMassAirFlow(generateMassAirFlow(engineRPM));
        return payload;
    }

    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    private static double generateRandomNumber(double max, double min) {
        return min + (max - min) * new Random().nextDouble();
    }

    //endregion

}

package device;

import java.util.Random;

/**
 * Random values between ranges - engine RPM, engine coolant temperature, mass air flow (as of now), throttle position
 *
 * Mass air flow linearly varies with engine rpm above 1000 RPM (<a href="https://www.researchgate.net/figure/Flow-rate-vs-rpm-in-higher-rpms-for-a-normal-sensor_fig5_280884222">Reference</a>)
 *
 * Throttle position approximately varies linearly with rpm with a slope of 634. (<a href="https://www.researchgate.net/figure/Position-of-the-throttle-valve-versus-engine-speed-at-idle-From-Figure-7-we-can-deduct_fig1_317158492">reference</a>)
 */
public class PayloadDataGenerator {
    public static int minEngineRPM = 1000;
    public static int maxEngineRPM = 6000;

    public static double minCoolantTemp = 30; // In celsius
    public static double maxCoolantTemp = 45;


    public static double minThrottlePosition = 12;
    public static double maxThrottlePosition = 17;

    public static double DTCsProbability = 0.02;


    public static int generateEngineRPM() {
        return (int) generateRandomNumber(minEngineRPM, maxEngineRPM);
    }

    public static double generateEngineCoolantTemp() {
        return generateRandomNumber(minCoolantTemp, maxCoolantTemp);
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
        return engineRPM / 640; //
    }

    public static double generateThrottlePosition() {
        return generateRandomNumber(minThrottlePosition, maxThrottlePosition);
    }

    public static boolean isDTC() {
        return new Random().nextDouble() < DTCsProbability;
    }


    public static boolean isDTCFixed(int distanceTravelled) {
        // We can use (1 - negative exponential function) with a constant to check the probability that the DTC is resolved on that day
        // Although this is hard, it's quite hard to compute and considering it's done at every iteration, it's quite a bad thing

        if (distanceTravelled > 5) {
            return true;
        }
        return false;
    }

    public PayloadData getRandomPayloadData() {
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

    private static double generateRandomNumber(double max, double min) {
        return min + (max - min) * new Random().nextDouble();
    }
}

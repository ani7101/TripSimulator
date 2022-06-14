package device;

import java.util.Random;

/**
 * Random values between ranges - engine RPM, engine coolant temperature, mass air flow (as of now), throttle position
 */
public class GeneratePayload {
    public int minEngineRPM = 1000;
    public int maxEngineRPM = 8000;

    public double minCoolantTemp = 30; // In celsius
    public double maxCoolantTemp = 45;

    public int minMassAirFlow = 90;
    public int maxMassAirFlow = 10;

    public double minThrottlePosition = 3.5;
    public double maxThrottlePosition = 4.7;

    public int averageFuelEconomy = 21;

    public double DTCsProbability = 0.02;


    public int generateEngineRPM() {
        return (int) generateRandomNumber(minEngineRPM, maxEngineRPM);
    }

    public double generateCoolantTemp() {
        return generateRandomNumber(minCoolantTemp, maxCoolantTemp);
    }

    public int generateMassAirFlow() {
        return (int) generateRandomNumber(minMassAirFlow, maxMassAirFlow);
    }

    public double generateThrottlePosition() {
        return generateRandomNumber(minThrottlePosition, maxThrottlePosition);
    }

    public boolean isDTC() {
        return new Random().nextDouble() < DTCsProbability;
    }

    public int getAverageFuelEconomy() {
        return averageFuelEconomy;
    }

    // Yet to figure out the relation between engine RPM and the throttle position
    // DTCs are kinda figured out
    // Mass air flow relation with throttle position and engine RPM

    public boolean isDTCFixed(int distanceTravelled) {
        // We can use (1 - negative exponential function) with a constant to check the probability that the DTC is resolved on that day
        // Although this is hard, it's quite hard to compute and considering it's done at every iteration, it's quite a bad thing
        return true;
    }

    private double generateRandomNumber(double max, double min) {
        return min + (max - min) * new Random().nextDouble();
    }


}

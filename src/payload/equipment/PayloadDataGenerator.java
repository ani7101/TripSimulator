package payload.equipment;

import java.util.Random;

public class PayloadDataGenerator {

    public static Random random = new Random();

    //region Constant values
    //---------------------------------------------------------------------------------------

    public static double BASE_VARIATION = 0.02;

    // Tilt (in degrees)
    public static double BASE_TILT = 0; // Completely horizontal


    // Higher the light (analog) sensor value, the area is darker
    public static int MIN_LIGHT = 1;
    public static int MAX_LIGHT = 4095;


    // Temperature variation range for temperature or ambient temperature (in Celsius)
    public static double BASE_TEMPERATURE = 25;


    // Humidity (in percentage)

    public static double HUMIDITY = 70;


    // Pressure (in ATMs)
    public static double PRESSURE = 1;


    // Shock values
    public static double MIN_SHOCK = 0;
    public static double MAX_SHOCK = 10;


    // Tamper detection (values between 0 to 1)

    public static double TAMPER_DETECTION_THRESHOLD = 0.7;


    //endregion
    //region Randomized generators
    //---------------------------------------------------------------------------------------

    public static double generateTilt() {
        return BASE_TILT + random.nextGaussian();
    }

    public static int generateLightSensor() {
        return (int) ((MIN_LIGHT + MAX_LIGHT) * (1 + BASE_VARIATION * random.nextGaussian()) / 2);
    }

    public static double generateTemperature() {
        return BASE_TEMPERATURE * (1 + BASE_VARIATION * random.nextGaussian());
    }

    public static double generatePressure() {
        return PRESSURE * (1 + BASE_VARIATION * random.nextGaussian());
    }

    public static double generateHumidity() {
        return HUMIDITY * (1 + BASE_VARIATION * random.nextGaussian());
    }

    public static double generateShock() {
        return generateRandomNumber(MIN_SHOCK, MAX_SHOCK);
    }

    public static double generateAmbientTemperature() {
        return BASE_TEMPERATURE + random.nextGaussian();
    }

    public static double generateTamperDetection() {
        return (TAMPER_DETECTION_THRESHOLD / 2.0) * (1 + BASE_VARIATION * random.nextGaussian());
    }


    //endregion
    //region Setters
    //---------------------------------------------------------------------------------------

    public static void setBaseTemperature(double baseTemperature) {
        BASE_TEMPERATURE = baseTemperature;
    }

    public static void setBaseTilt(double baseTilt) {
        BASE_TILT = baseTilt;
    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    private static double generateRandomNumber(double max, double min) {
        return min + (max - min) * new Random().nextDouble();
    }

    //endregion

}

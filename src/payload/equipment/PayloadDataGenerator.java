package payload.equipment;

import java.util.Random;

public class PayloadDataGenerator {

    //region Constant values
    //---------------------------------------------------------------------------------------

    // Tilt (in degrees)
    public static double BASE_TILT = 5;
    public static double TILT_VARIATION = 5;


    // Higher the light (analog) sensor value, the area is darker
    public static int MIN_LIGHT = 1;
    public static int MAX_LIGHT = 4095;


    // Temperature variation range for temperature or ambient temperature (in Celsius)
    public static double BASE_TEMPERATURE = 25;
    public static double BASE_AMBIENT_TEMPERATURE = 25;
    public static double TEMPERATURE_VARIATION = 5;


    // Humidity (in percentage)
    public static double MIN_HUMIDITY = 40;
    public static double MAX_HUMIDITY = 85;


    // Pressure (in ATMs)
    public static double MIN_PRESSURE = 0.5;
    public static double MAX_PRESSURE = 1.5;


    // Shock values
    public static double MIN_SHOCK = 0;
    public static double MAX_SHOCK = 10;


    // Tamper detection (values between 0 to 1)
    public static double MIN_TAMPER_DETECTION = 0;
    public static double MAX_TAMPER_DETECTION = 1;

    public static double TAMPER_DETECTION_THRESHOLD = 0.7;

    // Dropping off the equipment/ship unit/ship items
    public static double DROPPED_PROBABILITY = 0.05;


    //endregion
    //region Randomized generators
    //---------------------------------------------------------------------------------------

    public static double generateTilt() {
        return generateRandomNumber(BASE_TILT - TILT_VARIATION, BASE_TILT + TILT_VARIATION);
    }

    public static int generateLightSensor() {
        return (int) generateRandomNumber(MIN_LIGHT, MAX_LIGHT);
    }

    public static double generateTemperature() {
        return generateRandomNumber(BASE_TEMPERATURE - TEMPERATURE_VARIATION, BASE_TEMPERATURE + TEMPERATURE_VARIATION);
    }

    public static double generatePressure() {
        return generateRandomNumber(MIN_PRESSURE, MAX_PRESSURE);
    }

    public static double generateHumidity() {
        return generateRandomNumber(MIN_HUMIDITY, MAX_HUMIDITY);
    }

    public static double generateShock() {
        return generateRandomNumber(MIN_SHOCK, MAX_SHOCK);
    }

    public static double generateAmbientTemperature() {
        return generateRandomNumber(BASE_AMBIENT_TEMPERATURE - TEMPERATURE_VARIATION, BASE_AMBIENT_TEMPERATURE + TEMPERATURE_VARIATION);
    }

    public static double generateTamperDetection() {
        return generateRandomNumber(MIN_TAMPER_DETECTION, MAX_TAMPER_DETECTION);
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

    public static void setBaseAmbientTemperature(double baseAmbientTemperature) {
        BASE_AMBIENT_TEMPERATURE = baseAmbientTemperature;
    }

    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    private static double generateRandomNumber(double max, double min) {
        return min + (max - min) * new Random().nextDouble();
    }

    //endregion

}

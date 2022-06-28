package tests;

import org.testng.annotations.Test;
import trip.GeoLocationRepository;
import utils.CSVParser;

import java.util.ArrayList;
import java.util.List;

public class testGeoLocation {
    private GeoLocationRepository geoLocationRepository;

    @Test(priority = 1)
    public void loadRepository() {
        System.out.println("LOADING GEO-LOCATION");

        geoLocationRepository = new GeoLocationRepository(
                CSVParser.parseGeoLocation("locationRepository/sources.csv"),
                CSVParser.parseGeoLocation("locationRepository/destinations.csv"),
                CSVParser.parseGeoLocation("locationRepository/stops.csv")
        );

        System.out.println(geoLocationRepository.getSources());
        System.out.println(geoLocationRepository.getDestinations());
        System.out.println(geoLocationRepository.getStops());
    }

    @Test(priority = 2)
    public void testAddStops() {
        System.out.println("ADDING ADDITIONAL GEO-LOCATION");

        ArrayList<Double> dummyPoint = new ArrayList<>(List.of(0.0, 0.0));
        geoLocationRepository.addSource(dummyPoint);
        geoLocationRepository.addDestination(dummyPoint);
        geoLocationRepository.addStop(dummyPoint);

        System.out.println(geoLocationRepository.getSources() + ", number of elements: " + geoLocationRepository.sourcesCount());
        System.out.println(geoLocationRepository.getDestinations() + ", number of elements: " + geoLocationRepository.destinationsCount());
        System.out.println(geoLocationRepository.getStops() + ", number of elements: " + geoLocationRepository.stopsCount());
    }

    @Test(priority = 3)
    public void testRandomizer() {
        System.out.println("RANDOMIZER GEO-LOCATION");
        System.out.println(geoLocationRepository.getRandomSource());
        System.out.println(geoLocationRepository.getRandomDestination());
        System.out.println(geoLocationRepository.getRandomStop());
    }

    @Test(priority = 4)
    public void testRandomizedArrays() {
        System.out.println(geoLocationRepository.getRandomSource(2));
        System.out.println(geoLocationRepository.getRandomDestination(2));
        System.out.println(geoLocationRepository.getRandomStop(2));
    }
}

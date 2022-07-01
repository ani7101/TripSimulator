package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import trip.*;
import utils.CSVParser;

public class TestTrip {

    private TripAPIClient client;

    private Trip trip;

    private static final GeoLocationRepository geoLocationRepository;

    static {
        geoLocationRepository = new GeoLocationRepository(
                CSVParser.parseGeoLocation("locationRepository/sources.csv"),
                CSVParser.parseGeoLocation("locationRepository/destinations.csv"),
                CSVParser.parseGeoLocation("locationRepository/stops.csv")
        );
    }


    private final String accessTokenUrl = "https://gdhanani2-dev.internal.iot.ocs.oraclecloud.com/iotapps/privateclientapi/v2/oauth/hereMapToken";

    @Test(priority = 1)
    @Parameters({"baseUrl", "username", "password", "vehicleName"})
    public void testConfigAndRandomizer(String baseUrl, String username, String password, String vehicleName) {
        client = new TripAPIClient(baseUrl, username, password);

        trip = TripGenerator.randomizedTripFromVehicle(
                accessTokenUrl, username, password,
                baseUrl, username, password, vehicleName, "TestTrip", geoLocationRepository,  "ORA_DEFAULT_ORG", 1);
    }

    @Test(priority = 2)
    public void testTripCreation() {
        client.create(trip);
    }

    @Test(priority = 2)
    public void testFetchAll() {
        client.getAll();
    }

    @Test(priority = 2)
    public void testFetchCount() {
        int count = client.getCount();
        assert count >= 0;
    }

    @Test(priority = 3)
    @Parameters({"trip"})
    public void testFetchOne(String tripId) {
        client.getOne(tripId);
    }

    // Extras
    @Test(priority = 4)
    @Parameters({"trip"})
    public void testFetchMetrics(String tripId) {
        System.out.println(client.getMetrics(tripId));
    }

}

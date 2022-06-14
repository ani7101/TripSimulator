package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import trip.*;

public class TestTrip {

    private TripRepository repository;

    private TripAPIClient client;

    private PopulateTrip trip;

    @BeforeTest()
    public void setupTestRepository() {
        repository = new TripRepository();
        // Need to fill the trip repository
    }


    @Test(priority = 1)
    @Parameters({"baseUrl", "username", "password", "vehicleType"})
    public void testConfigAndRandomizer(String baseUrl, String username, String password, String vehicleType) {
        client = new TripAPIClient(baseUrl, username, password);

        trip = new PopulateTrip(baseUrl, username, password, repository, vehicleType, true);
    }

    @Test(priority = 2)
    public void testTripCreation() {
        trip.sendQuery();
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

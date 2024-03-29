package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import vehicle.*;

public class TestVehicles {
    private VehicleAPIClient client;
    private Vehicle vehicle;


    @Test(priority = 1)
    @Parameters({"baseUrl", "username", "password", "deviceId"})
    public void testConfigAndRandomizer(String baseUrl, String username, String password, String deviceId) {
        client = new VehicleAPIClient(baseUrl, username, password);
        vehicle = OBD2VehicleGenerator.randomizedVehicle(baseUrl, username, password, deviceId, "-Test-");
    }

    @Test(priority = 2)
    public void testTypeCreation() {
        client.create(vehicle);
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
    @Parameters({"vehicle"})
    public void testFetchOne(String vehicleId) {
        System.out.println(client.getOne(vehicleId));
    }

    // Extras
    @Test(priority = 4)
    @Parameters({"vehicle"})
    public void testFetchMetrics(String vehicleId) {
        System.out.println(client.getMetrics(vehicleId));
    }

    @Test(priority = 4)
    @Parameters({"vehicle"})
    public void testFetchOBD2Parameters(String vehicleId) {
        System.out.println(client.getOBD2Parameters(vehicleId));
    }
}

package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import vehicle.*;

public class TestVehicles {
    private VehicleAPIClient client;
    private GeneratedOBD2Vehicle vehicle;

    @Test(priority = 1)
    @Parameters({"baseUrl", "username", "password", "vehicleType"})
    public void testConfigAndRandomizer(String baseUrl, String username, String password, String vehicleType) {
        client = new VehicleAPIClient(baseUrl, username, password);
        vehicle = new GeneratedOBD2Vehicle(baseUrl, username, password, vehicleType);
    }

    @Test(priority = 2)
    public void testTypeCreation() {
        vehicle.sendQuery();
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
        client.getOne(vehicleId);
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

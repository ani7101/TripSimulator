package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import vehicleType.*;

import java.util.ArrayList;

public class TestVehicleTypes {
    private VehicleTypeAPIClient client;
    private PopulateOBD2VehicleType type;

    @Test(priority = 1)
    @Parameters({"baseUrl", "username", "password"})
    public void testConfigAndRandomizer(String baseUrl, String username, String password) {
        client = new VehicleTypeAPIClient(baseUrl, username, password);
        type = new PopulateOBD2VehicleType(baseUrl, username, password);
    }

    @Test(priority = 2)
    public void testTypeCreation() {
        type.sendQuery();
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
    @Parameters({"vehicleType"})
    public void testFetchOne(String typeId) {
        client.getOne(typeId);
    }

    @Test(priority = 3)
    public void testFetchSeededFields() {
        String response = client.getPreSeededFields();
        System.out.println(response);
    }
}

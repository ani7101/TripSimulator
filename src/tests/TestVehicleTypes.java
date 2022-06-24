package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import vehicleType.*;

public class TestVehicleTypes {
    private VehicleTypeAPIClient client;

    private VehicleType obd2Type;

    @Test(priority = 1)
    @Parameters({"baseUrl", "username", "password"})
    public void testConfigAndRandomizer(String baseUrl, String username, String password) {
        client = new VehicleTypeAPIClient(baseUrl, username, password);
        obd2Type = OBD2VehicleTypeGenerator.randomizedType();
    }

    @Test(priority = 2)
    public void testTypeCreation() {
        client.create(obd2Type);
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
        VehicleType type = client.getOne(typeId);
    }

    @Test(priority = 3)
    public void testFetchSeededFields() {
        String response = client.getPreSeededFields();
        System.out.println(response);
    }
}

package tests;

import device.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class TestDevice {
    DeviceAPIClient client;
    DeviceConnectorWrapper wrapper;

    @Test(priority = 1)
    @Parameters({"baseUrl", "connectorUrl", "username", "password"})
    public void testConfig(String baseUrl, String connectorUrl, String username, String password) {
        client = new DeviceAPIClient(baseUrl, username, password);
        wrapper = new DeviceConnectorWrapper(baseUrl, connectorUrl, username, password, 3);
    }

    @Test(priority = 2)
    public void testFetchAll() {
        System.out.println(client.getAll());
    }

    @Test(priority = 3)
    public void testWrapper() {
        wrapper.populatePayload();
        ArrayList<String> deviceIdentifiers = wrapper.getDeviceIdentifiers();

        wrapper.createDeviceUsingConnector();

        for (String deviceIdentifier : deviceIdentifiers) {
            Device d1 = wrapper.searchDeviceId(deviceIdentifier);
            System.out.println("Device:\nID:" + d1.getId() + "\nHardwareID: " + d1.getHardwareId());
        }
    }
}
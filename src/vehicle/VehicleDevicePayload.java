package vehicle;

import device.Device;
import device.Payload;

public class VehicleDevicePayload {

    private String uniqueId;

    private Device device;

    private GeneratedOBD2Vehicle generatedOBD2Vehicle;

    private Payload payload;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public GeneratedOBD2Vehicle getGeneratedOBD2Vehicle() {
        return generatedOBD2Vehicle;
    }

    public void setGeneratedOBD2Vehicle(GeneratedOBD2Vehicle generatedOBD2Vehicle) {
        this.generatedOBD2Vehicle = generatedOBD2Vehicle;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}

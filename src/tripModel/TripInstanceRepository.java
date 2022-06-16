package tripModel;

import device.Device;
import trip.*;
import user.GeneratedUser;
import vehicle.*;

import java.util.ArrayList;

public class TripInstanceRepository {
   private ArrayList<TripSimulatorInstance> instances;

   private VehicleDeviceWrapper vehicleDeviceWrapper;

   private ArrayList<GeneratedUser> generatedUsers;

   private TripRepository repository = new TripRepository();


   public TripInstanceRepository(String baseUrl, String connectorUrl, String username, String password,
                                 int requiredInstances,
                                 String organizationId) {
       instances = new ArrayList<TripSimulatorInstance>(requiredInstances);

       // The following command will create the requiredInstances number of vehicles and link the devices along with it
       vehicleDeviceWrapper = new VehicleDeviceWrapper(baseUrl, connectorUrl, username, password, requiredInstances);

        ArrayList<VehicleDevicePayload> vehicleDevicePayloads = vehicleDeviceWrapper.getVehicleDevicePayloads();

       for (int i = 0; i < requiredInstances; i++) {

           GeneratedUser generatedUser = new GeneratedUser(baseUrl, username, password, organizationId);
           generatedUser.sendQuery(); // To upload to the IoT API
           generatedUsers.add(generatedUser);

           // Create a trip and storing it to Trip
           GeneratedTrip tempGeneratedTrip = new GeneratedTrip(baseUrl, username, password, repository, vehicleDevicePayloads.get(i).getGeneratedOBD2Vehicle().getVehicle().getName(), generatedUser.getUser().getName());

           Trip tempTrip = tempGeneratedTrip.getTrip();

           // Now to parse this information into a simulator instance
           instances.add(new TripSimulatorInstance(
                   tempTrip.getId(),
                   tempTrip.getDriver().getLoginId(),
                   tempTrip.getVehicle().getName(),
                   vehicleDevicePayloads.get(i).getDevice().getId(),
                   tempTrip.getSource(),
                   tempTrip.getDestination(),
                   tempTrip.getStops(),
                   tempTrip.getRoute()
           ));


       }
   }

}

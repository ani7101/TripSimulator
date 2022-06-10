package tripStorage;

import trip.*;

import java.util.HashMap;
import java.util.Map;

public class TripStorage {
    private Map<String, Trip> storage;

    private int noEntities;
    private int count;
    private PopulateTrip populateTrip;

    public TripStorage(int noEntities, TripRepository repository) {
        this.noEntities = noEntities;
        storage = new HashMap<String, Trip>(noEntities);
        this.repository = repository;
        // PopulateTrip = new PopulateTrip("https://aniragha-lite.internal.iot.ocs.oraclecloud.com", "iot-cloudops_ww_grp", "Welcome1234#");
    }
    public TripRepository repository;

    public void populateEntities() {
        Trip temp;
        for (int i = 0; i < noEntities; i++) {
            temp = generateRandomTrip();
            storage.put(temp.getName(), temp);
        }
    }

    private Trip generateRandomTrip() {
        // Naming convention for a trip: 'Trip{trip_number}' and this trip_number is based on the current size of the storage.
        Trip trip = new Trip();
        trip.setName("trip" + storage.size() + 1);
        // Can directly use the DAO to obtain the trip instance as well as upload to the server.
        // Can be altered to return a minimal version of the trip having only required information and ignore the rest

        return trip;
    }
}
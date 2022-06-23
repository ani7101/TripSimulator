package bulkGenerators;

import simulation.models.TripModel;
import trip.Trip;
import trip.TripAPIClient;
import trip.TripGenerator;
import trip.subclasses.TripStopRecord;
import utils.Generator;
import utils.PolylineEncoderDecoder.LatLngZ;
import vehicle.Vehicle;

import java.io.IOException;
import java.util.ArrayList;

public class TripBulkGenerator {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private TripBulkGenerator() {
        throw new AssertionError();
    }

    public static ArrayList<TripModel> bulkCreateTrips(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String baseUrl,
            String connectorUrl,
            String username,
            String password,
            int requiredTrips,
            int noStops
    ) {
        // Initialise the API client
        TripAPIClient tripClient = new TripAPIClient(baseUrl, username, password);

        // Generating a list of unique IDs
        ArrayList<String> uniqueIds = Generator.generateRandomUUID(requiredTrips);

        // Creating the vehicles
        ArrayList<Vehicle> vehicles = VehicleBulkGenerator.bulkCreateVehicles(baseUrl, connectorUrl, username, password, uniqueIds, requiredTrips);

        ArrayList<TripModel> tripModels = new ArrayList<>(requiredTrips);

        // Creating trips
        for (int i = 0; i < requiredTrips; i++) {
            Trip trip = TripGenerator.randomizedTripFromVehicle(
                    accessTokenUrl, accessTokenUsername, accessTokenPassword, baseUrl, username, password, vehicles.get(i).getName(), uniqueIds.get(i), noStops);


            // Getting and updating the trip ID from the server response
            Trip responseTrip = tripClient.create(
                    TripGenerator.randomizedTripFromVehicle(
                            accessTokenUrl, accessTokenUsername, accessTokenPassword, baseUrl, username, password, vehicles.get(i).getName(), uniqueIds.get(i), noStops)
            );
            trip.setId(responseTrip.getId());

            tripModels.add(parseTripToTripModel(trip, vehicles.get(i).getDeviceId(), vehicles.get(i).getDeviceName()));
        }

        return tripModels;
    }

    public static ArrayList<TripModel> bulkCreateTrips(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String baseUrl,
            String connectorUrl,
            String username,
            String password,
            String vehicleType,
            int requiredTrips,
            int noStops
    ) {
        // Initialise the API client
        TripAPIClient tripClient = new TripAPIClient(baseUrl, username, password);

        // Generating a list of unique IDs
        ArrayList<String> uniqueIds = Generator.generateRandomUUID(requiredTrips);

        // Creating the vehicles
        ArrayList<Vehicle> vehicles = VehicleBulkGenerator.bulkCreateVehicles(baseUrl, connectorUrl, username, password, vehicleType, uniqueIds, requiredTrips);

        ArrayList<TripModel> tripModels = new ArrayList<>(requiredTrips);

        // Creating trips
        for (int i = 0; i < requiredTrips; i++) {
            Trip trip = TripGenerator.randomizedTripFromVehicle(accessTokenUrl, accessTokenUsername, accessTokenPassword, baseUrl, username, password, vehicles.get(i).getName(), uniqueIds.get(i), noStops);


            // Getting and updating the trip ID from the server response
            Trip responseTrip = tripClient.create(
                    TripGenerator.randomizedTripFromVehicle(accessTokenUrl, accessTokenUsername, accessTokenPassword, baseUrl, username, password, vehicles.get(i).getName(), uniqueIds.get(i), noStops)
            );

            trip.setId(responseTrip.getId());

            tripModels.add(parseTripToTripModel(trip, vehicles.get(i).getDeviceId(), vehicles.get(i).getDeviceName()));
        }

        return tripModels;
    }

    private static TripModel parseTripToTripModel(Trip trip, String deviceId, String deviceName) {
        LatLngZ source = new LatLngZ(trip.getSource().getLatitude(), trip.getSource().getLongitude());
        LatLngZ destination = new LatLngZ(trip.getDestination().getLatitude(), trip.getDestination().getLongitude());

        ArrayList<LatLngZ> stops = new ArrayList<>();
        for (TripStopRecord stop : trip.getStops()) {
            stops.add(new LatLngZ(stop.getLatitude(), stop.getLongitude()));

        }

        return new TripModel(
                trip.getId(),
                trip.getDriver().getLoginId(),
                trip.getVehicle().getName(),
                deviceId,
                deviceName,
                source,
                destination,
                stops,
                trip.getRoute()
        );
    }

    private static void pause() {
        try {
            System.out.println("Stop or start the the VPN and type a random word and ENTER");
            System.in.read();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

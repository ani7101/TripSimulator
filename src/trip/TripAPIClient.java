package trip;

import trip.subclasses.TripList;
import utils.APIClient;
import utils.ParseJson;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Accesses the trip API of the IoT server instance.
 * The API Documentation can be referred via this
 * <a href="https://docs.oracle.com/en/cloud/saas/iot-fleet-cloud/rest-api/api-trip-management.html">link</a>
 */
public class TripAPIClient extends APIClient {
    private final String authHeader;
    private final String baseUrl;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    //region Constructors
    //---------------------------------------------------------------------------------------

    /**
     * Initializes the trip API client with the input baseUrl and basic authentication username and password.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     */
    public TripAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }


    //endregion
    //region API Client methods
    //---------------------------------------------------------------------------------------

    /**
     * Sends a request to the IoT server instance FM API to get all trips.
     * @return ArrayList(Trip): List of all trips
     */
    public ArrayList<Trip> getAll() {
        ArrayList<Trip> list = null;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/trips/", authHeader);
            list = ParseJson.deserializeResponse(json, TripList.class).getItems();
        } catch (Exception e) {
            LOGGER.warning("Exception @TripAPIClient: " + e);
        }

        return list;
    }

    /**
     * Sends a request to the IoT server instance FM API to get the trip with the corresponding ID.
     * @param tripId ID (identifier) to access the trip
     * @return Trip: Trip having the input ID
     */
    public Trip getOne(String tripId) {
        Trip response = null;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId, authHeader);
            response = ParseJson.deserializeResponse(json, Trip.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @TripAPIClient: " + e);
        }

        return response;
    }

    /**
     * Sends a request to the IoT server instance FM API to get the trip count.
     * @return int: Number of trips
     */
    public int getCount() {
        int count = 0;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/trips/count", authHeader);
            count = ParseJson.deserializeCountResponse(json);
        } catch (Exception e) {
            LOGGER.warning("Exception @TripAPIClient: " + e);
        }

        return count;
    }

    /**
     * Sends a request to the IoT server instance FM API to get the metrics of a trip.
     * @param tripId
     * @return String: metrics json body
     */
    public String getMetrics(String tripId) {
        String response = null;

        try {
            response = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId + "/metrics", authHeader);
        } catch (Exception e) {
            LOGGER.warning("Exception @TripAPIClient: " + e);
        }

        return response;
    }

    /**
     * Sends a request to the IoT server instance FM API to create a trip.
     * @param trip Trip instances to be converted to the json body
     * @return Trip: Response from the API call
     */
    public Trip create(Trip trip) {
        Trip response = null;

        try {
            String json = AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/trips/", authHeader, POJOToJson(trip));
            response = ParseJson.deserializeResponse(json, Trip.class);

        } catch (Exception e) {
            LOGGER.warning("Exception @TripAPIClient: " + e);
        }

        return response;
    }

    /**
     * Sends a request to the IoT server instance FM API to update trip with input ID.
     * @param tripId ID (identifier) to access the trip
     * @param updatedTrip Updated trip values
     * @return Trip: Response from the API call
     */
    public Trip update(String tripId, Trip updatedTrip) {
        Trip response = null;

        try {
            String json = AsyncUPDATE(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId, authHeader, POJOToJson(updatedTrip));
            response = ParseJson.deserializeResponse(json, Trip.class);

        } catch (Exception e) {
            LOGGER.warning("Exception @TripAPIClient: " + e);
        }

        return response;
    }

    /**
     * Sends a request to the IoT server instance FM API to delete a trip.
     * @param tripId ID (identifier) to access the trip to be deleted
     */
    public void delete(String tripId) {
        try {
            AsyncDELETE(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId, authHeader);
        } catch (Exception e) {
            LOGGER.warning("Exception @TripAPIClient: " + e);
        }
    }

    //endregion

}

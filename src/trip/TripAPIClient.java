package trip;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import trip.subclasses.TripList;
import utils.APIClient;
import utils.ParseJson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Accesses the trip API of the IoT server instance.
 * The API Documentation can be referred via this
 * <a href="https://docs.oracle.com/en/cloud/saas/iot-fleet-cloud/rest-api/api-trip-management.html">link</a>
 */
public class TripAPIClient extends APIClient {
    private final String authHeader;
    private final String baseUrl;

    private static final Logger IOT_API_LOGGER = LoggerFactory.getLogger("iot-api");


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
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting all trips:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting all trips:", e);
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
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting one trip:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting one trip:", e);
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
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting the trips count:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting the trips count:", e);
        }

        return count;
    }

    /**
     * Sends a request to the IoT server instance FM API to get the metrics of a trip.
     * @param tripId Identifier to the trip whose metrics are to be retrieved
     * @return String: metrics json body
     */
    public String getMetrics(String tripId) {
        String response = null;

        try {
            response = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId + "/metrics", authHeader);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting trip metrics:", e);
        } catch (TimeoutException e) {
            IOT_API_LOGGER.warn("Exception while getting trip metrics:", e);
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

        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while creating a trip:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while creating a trip:", e);
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

        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while updating a trip:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while updating a trip:", e);
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
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while deleting a trip:", e);
        } catch (TimeoutException e) {
            IOT_API_LOGGER.warn("Exception while deleting a trip:", e);
        }
    }

    //endregion
    //region Utils

    public void cleanUp() {
        for (Trip trip : getAll()) {
            if (trip.getName().contains("Trip simulator")) {
                delete(trip.getId());
            }
        }
    }

    //endregion

}

package trip;

import trip.subclasses.TripList;
import utils.APIClient;
import utils.ParseJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;


public class TripAPIClient extends APIClient {
    private final String authHeader;
    private final String baseUrl;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public TripAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }

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

    public Trip create(Map<String, Object> formData) {
        Trip response = null;

        try {
            String formDataStr = new ObjectMapper().writeValueAsString(formData);
            String json = AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/trips/", authHeader, formDataStr);
            response = ParseJson.deserializeResponse(json, Trip.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @TripAPIClient: " + e);
        }

        return response;
    }

    public Trip create(Trip trip) {
        Trip response = null;

        try {
            String form = POJOToJson(trip);
            String json = AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/trips/", authHeader, form);
            System.out.println(json);
            response = ParseJson.deserializeResponse(json, Trip.class);

        } catch (Exception e) {
            e.printStackTrace();
//            LOGGER.warning("Exception @TripAPIClient: " + e);
        }

        return response;
    }

    public Trip update(String tripId, Map<String, Object> formData) {
        Trip response = null;

        try {
            String formDataStr = new ObjectMapper().writeValueAsString(formData);

            String json = AsyncUPDATE(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId, authHeader, formDataStr);
            response = ParseJson.deserializeResponse(json, Trip.class);

        } catch (Exception e) {
            LOGGER.warning("Exception @TripAPIClient: " + e);
        }

        return response;
    }

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

    public void delete(String tripId) {
        try {
            AsyncDELETE(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId, authHeader);
        } catch (Exception e) {
            LOGGER.warning("Exception @TripAPIClient: " + e);
        }
    }

    public String getMetrics(String tripId) {
        String response = null;

        try {
            response = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId + "/metrics", authHeader);
        } catch (Exception e) {
            LOGGER.warning("Exception @TripAPIClient: " + e);
        }

        return response;
    }

}

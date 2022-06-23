package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * De-serializes json strings using jackson module
 */
public class ParseJson {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private ParseJson() {
        throw new AssertionError();
    }

    /**
     * Deserializes a json string to a specified class instance
     * @param serverResponse Json string to be deserialized
     * @param modelType Model for the data to be de-serialized into
     * @return (T): Deserialized class having the data of the json string
     * @param <T> Class model of the response
     */
    public static <T> T deserializeResponse(String serverResponse, Class<T> modelType) {
        T modelObject = null;
        try {
            modelObject = new ObjectMapper().readValue(serverResponse, modelType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelObject;
    }

    /**
     * eserializes a json string to get the count (Based on the IoT server response format)
     * @param serverResponse Json string to be deserialized
     * @return int: count value in the json response
     */
    public static int deserializeCountResponse(String serverResponse) {
        int count = 0;
        try {
            ObjectNode node = new ObjectMapper().readValue(serverResponse, ObjectNode.class);
            if (node.has("count")) {
                count = node.get("count").asInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Deserializes the json string to a map
     * @param serverResponse Json string to be deserialized
     * @return Map(String, Object): Map containing the deserialized json
     */
    public static Map<String, Object> deserializeResponse(String serverResponse){
        Map<String, Object> result = null;
        try {
            result = new ObjectMapper().readValue(serverResponse, HashMap.class);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }

        return result;
    }
}

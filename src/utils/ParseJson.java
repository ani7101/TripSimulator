package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParseJson {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private ParseJson() {
        throw new AssertionError();
    }


    public static <T> T deserializeResponse(String serverResponse, Class<T> modelType) {
        T modelObject = null;
        try {
            modelObject = new ObjectMapper().readValue(serverResponse, modelType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelObject;
    }

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

    public static Map<String, Object> deserializeResponse(String serverResponse) throws JsonProcessingException {
        return new ObjectMapper().readValue(serverResponse, HashMap.class);
    }
}

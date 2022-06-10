package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hereMaps.HEREMapsRouteSection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParseJson {
    public static <T> T deserializeResponse(String serverResponse, Class<T> modelType) {
        T modelObject = null;
        try {
            modelObject = new ObjectMapper().readValue(serverResponse, modelType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelObject;
    }

    // FIXME: 10/06/2022 Fix the deserializer to the appropriate class
    public static <T> T deserializeIoTResponse(String serverResponse, T modelClass) {
        T response = null;

        try {
            response = new ObjectMapper().readValue(serverResponse, new TypeReference<T>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    // TODO: 10/06/2022 Simplify this and eliminate the HEREMaps deserializer sub-classes
    public static <T> ArrayList<T> deserializeHEREAPIListResponse(String serverResponse, T modelClass) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<T> list = null;

        try {
            ObjectNode node = mapper.readValue(serverResponse, ObjectNode.class);
            System.out.println(node.has("routes") && node.get("routes").get(0).has("sections"));


            if (node.has("routes") && node.get("routes").get(0).has("sections")) {
                JsonNode temp = (JsonNode) node.get("routes").get(0).get("sections");
                ObjectReader reader = mapper.readerFor(new TypeReference<ArrayList<T>>() {});
                list = reader.readValue(temp);


                String jsonList = mapper.writeValueAsString(node.get("routes").get(0).get("sections"));
                list = mapper.readValue(jsonList, new TypeReference<ArrayList<T>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> int deserializeCountResponse(String serverResponse) {
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

package Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParseJson {
    public static <T> T deserializeResponse(String serverResponse, Class<T> modelType) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        T modelObject = null;
        try {
            modelObject = mapper.readValue(serverResponse, modelType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelObject;
    }

    public static Map<String, Object> deserializeResponse(String serverResponse) throws JsonProcessingException {
        return new ObjectMapper().readValue(serverResponse, HashMap.class);
    }
}

package config.util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json {
    //Create the object mapper with the default obj mapper method immediately
    private static ObjectMapper objectMapper = defaultobjectMapper();

    //Creates the object mapper and wont crash if the json has extra fields
    private static ObjectMapper defaultobjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }

    //Parses Json string into a JsonNode using Jackson tree
    public static JsonNode parse(String jsonString) throws IOException {
        return objectMapper.readTree(jsonString);
    }

    //Converts a JsonNode into a Java object of the specified class, ex: Turns a JsonNode into a Configuration object
    public static <A> A fromJson(JsonNode node, Class<A> thisClass) throws JsonProcessingException {
        return objectMapper.treeToValue(node, thisClass);
    }

    //Change obj to JsonNode
    public static JsonNode toJson(Object obj) {
        return objectMapper.valueToTree(obj);
    }

    //For ugly json
    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    //For json string formatted with indentation:
    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }

    //Convert any object or JsonNode into readable Json with optional formatting option (pretty)
    public static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
        ObjectWriter objWriter = objectMapper.writer();
        if (pretty) {
            objWriter = objWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objWriter.writeValueAsString(o);
    }


}

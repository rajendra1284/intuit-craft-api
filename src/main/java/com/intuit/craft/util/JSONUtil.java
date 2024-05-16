package com.intuit.craft.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(Feature.IGNORE_UNKNOWN, true);
        MAPPER.setSerializationInclusion(Include.NON_NULL);
        MAPPER.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Serialize the given object to string
     * 
     * @param object
     * @return String
     * @throws JsonProcessingException
     */
    public static String serialize(final Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    /**
     * De-serialize the given object to string
     * 
     * @param object
     * @return String
     * @throws JsonProcessingException
     */
    public static <T> T deserialize(final String data, final Class<T> classType) throws JsonProcessingException {
        return MAPPER.readValue(data, classType);
    }

    /**
     * De-serialize the given Type reference to string
     * 
     * @param object
     * @return String
     * @throws JsonProcessingException
     */
    public static <T> T deserialize(final String data, final TypeReference<T> valueType)
            throws JsonProcessingException {
        return MAPPER.readValue(data, valueType);
    }
}

package com.pro_crafting.tools.recordjarconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.pro_crafting.tools.recordjarconverter.representation.json.ObjectMapperProvider;

import java.lang.reflect.Type;

public class JsonSchemaUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final JsonSchemaGenerator GENERATOR = new JsonSchemaGenerator(MAPPER);

    static {
        ObjectMapperProvider provider = new ObjectMapperProvider();
        provider.customize(MAPPER);
    }

    public static String generateJsonSchema(TypeReference<?> typeReference) throws JsonProcessingException {
        if (typeReference == null) {
            return null;
        }

        return generateJsonSchema(typeReference.getType());
    }

    public static String generateJsonSchema(Type type) throws JsonProcessingException {
        JsonSchema jsonSchema = GENERATOR.generateSchema(MAPPER.getTypeFactory().constructType(type));

        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
    }
}

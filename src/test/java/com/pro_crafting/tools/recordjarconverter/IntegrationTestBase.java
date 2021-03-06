package com.pro_crafting.tools.recordjarconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.pro_crafting.tools.recordjarconverter.representation.json.ObjectMapperProvider;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Type;
import java.net.URI;

@ExtendWith(InitializeExtension.class)
public abstract class IntegrationTestBase {
    private static ObjectMapper mapper = new ObjectMapper();
    private JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);

    @BeforeAll
    static void beforeAll() {
        ObjectMapperProvider provider = new ObjectMapperProvider();
        provider.customize(mapper);

        RestAssured.baseURI = "http://"+System.getProperties().getProperty("it.baseuri", "127.0.0.1/");
        RestAssured.port = Integer.parseInt(System.getProperties().getProperty("it.port", "8080"));
        //enableLoggingOfRequestAndResponseIfValidationFails();
    }

    protected String generateJsonSchema(TypeReference<?> typeReference) throws JsonProcessingException {
        if (typeReference == null) {
            return null;
        }

        return generateJsonSchema(typeReference.getType());
    }

    protected String generateJsonSchema(Type type) throws JsonProcessingException {
        JsonSchema jsonSchema = generator.generateSchema(mapper.getTypeFactory().constructType(type));

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
    }
}

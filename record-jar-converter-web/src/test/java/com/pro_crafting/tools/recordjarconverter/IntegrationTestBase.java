package com.pro_crafting.tools.recordjarconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;

@ExtendWith(InitializeExtension.class)
public abstract class IntegrationTestBase {
    private ObjectMapper mapper = new ObjectMapper();
    private JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);

    @BeforeAll
    static void beforeAll() {

        RestAssured.baseURI = System.getProperties().getProperty("it.baseuri", "http://127.0.0.1/");
        RestAssured.port = Integer.parseInt(System.getProperties().getProperty("it.port", "8080"));
        enableLoggingOfRequestAndResponseIfValidationFails();
    }

    protected String generateJsonSchema(TypeReference<?> typeReference) throws JsonProcessingException {
        if (typeReference == null) {
            return null;
        }

        return generateJsonSchema(typeReference.getType());
    }

    protected String generateJsonSchema(Type type) throws JsonProcessingException {
        SimpleModule module = new SimpleModule();

        JsonSchema jsonSchema = generator.generateSchema(mapper.getTypeFactory().constructType(type));

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
    }
}

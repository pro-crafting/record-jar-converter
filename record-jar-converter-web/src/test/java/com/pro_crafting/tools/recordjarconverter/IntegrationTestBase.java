package com.pro_crafting.tools.recordjarconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import io.restassured.RestAssured;
import org.jboss.weld.junit5.EnableWeld;
import org.junit.jupiter.api.BeforeAll;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;

public abstract class IntegrationTestBase {
    private ObjectMapper mapper = new ObjectMapper();
    private JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "http://127.0.0.1/";
        RestAssured.port = 8080;
        enableLoggingOfRequestAndResponseIfValidationFails();
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

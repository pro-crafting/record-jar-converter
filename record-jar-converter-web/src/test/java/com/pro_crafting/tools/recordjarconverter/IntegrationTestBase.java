package com.pro_crafting.tools.recordjarconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.pro_crafting.tools.recordjarconverter.representation.json.ObjectMapperProvider;
import com.sun.jndi.toolkit.url.Uri;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Type;
import java.net.URI;

@ExtendWith(InitializeExtension.class)
public abstract class IntegrationTestBase {
    private ObjectMapper mapper = ObjectMapperProvider.getMapper();
    private JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);

    @BeforeAll
    static void beforeAll() {

        RestAssured.baseURI = URI.create(System.getProperties().getProperty("it.baseuri", "http://127.0.0.1/")).getHost();
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

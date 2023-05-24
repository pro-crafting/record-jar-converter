package com.pro_crafting.tools.recordjarconverter;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.get;

@QuarkusTest
public class StatusIT extends JsonSchemaUtil {
    @Test
    void testOutcomeUp() {
        get("/q/health")
                .then()
                .body("status", equalTo("UP"));
    }
}

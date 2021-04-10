package com.pro_crafting.tools.recordjarconverter;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.get;

public class StatusIT extends IntegrationTestBase {
    @Test
    void testOutcomeUp() {
        get("/health")
                .then()
                .body("status", equalTo("UP"));
    }
}

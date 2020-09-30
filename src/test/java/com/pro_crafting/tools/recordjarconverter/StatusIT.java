package com.pro_crafting.tools.recordjarconverter;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.get;

public class StatusIT extends IntegrationTestBase {
    @Test
    void testOutcomeUp() {
        get("/health")
                .then()
                .body("outcome", equalTo("UP"));
    }
}

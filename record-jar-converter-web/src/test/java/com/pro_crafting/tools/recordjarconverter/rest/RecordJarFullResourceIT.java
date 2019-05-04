package com.pro_crafting.tools.recordjarconverter.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pro_crafting.tools.recordjarconverter.IntegrationTestBase;
import com.pro_crafting.tools.recordjarconverter.RestApplication;
import com.pro_crafting.tools.recordjarconverter.service.model.Record;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.commons.io.IOUtils.resourceToByteArray;
import static org.apache.commons.io.IOUtils.resourceToString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecordJarFullResourceIT extends IntegrationTestBase {

    private static String MULTIPART_FORM_FILE_PATH = RestApplication.VERSION_PATH + RecordJarFullResource.RESOURCE_PATH + "multipart/file";
    private static String MULTIPART_FORM_TEXT_PATH = RestApplication.VERSION_PATH + RecordJarFullResource.RESOURCE_PATH + "multipart/text";
    private static String TEXT_PATH = RestApplication.VERSION_PATH + RecordJarFullResource.RESOURCE_PATH + "text";

    @Test
    void testUploadMultipartFile() throws IOException {
        byte[] bytes = resourceToByteArray("taoup-rj-example.rj", getClass().getClassLoader());
        String expected = resourceToString("taoup-rj-converted-full.json", Charset.forName("UTF-8"), getClass().getClassLoader());
        Response response = given()
                .multiPart("file", "language-subtag-registry.rj", bytes)
                .multiPart("encoding", "UTF-8")
                .expect()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .when()
                .post(MULTIPART_FORM_FILE_PATH);

        assertEquals(expected, response.asString());
    }

    @Test
    void testUploadMultipartFileLarge() throws IOException {
        String schema = super.generateJsonSchema(new TypeReference<List<Record>>() {});
        byte[] bytes = resourceToByteArray("language-subtag-registry.rj", getClass().getClassLoader());
        Response response = given()
                .multiPart("file", "language-subtag-registry.rj", bytes)
                .multiPart("encoding", "UTF-8")
                .expect()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchema(schema))
                .when()
                .post(MULTIPART_FORM_FILE_PATH);
    }

    @Test
    void testUploadMultipartText() throws IOException {
        String body = resourceToString("taoup-rj-example.rj", Charset.forName("UTF-8"), getClass().getClassLoader());
        String expected = resourceToString("taoup-rj-converted-full.json", Charset.forName("UTF-8"), getClass().getClassLoader());
        Response response = given()
                .multiPart("text", body)
                .multiPart("encoding", "UTF-8")
                .expect()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .when()
                .post(MULTIPART_FORM_TEXT_PATH);

        assertEquals(expected, response.asString());
    }

    @Test
    void testUploadMultipartTextLarge() throws IOException {
        String schema = super.generateJsonSchema(new TypeReference<List<Record>>() {});
        String body = resourceToString("language-subtag-registry.rj", Charset.forName("UTF-8"), getClass().getClassLoader());
        Response response = given()
                .multiPart("text", body)
                .multiPart("encoding", "UTF-8")
                .expect()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchema(schema))
                .when()
                .post(MULTIPART_FORM_TEXT_PATH);
    }

    @Test
    void testUploadText() throws IOException {
        String body = resourceToString("taoup-rj-example.rj", Charset.forName("UTF-8"), getClass().getClassLoader());
        String expected = resourceToString("taoup-rj-converted-full.json", Charset.forName("UTF-8"), getClass().getClassLoader());
        Response response = given()
                .body(body)
                .queryParam("encoding", "UTF-8")
                .expect()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .when()
                .post(TEXT_PATH);

        assertEquals(expected, response.asString());
    }

    @Test
    void testUploadTextLarge() throws IOException {
        String schema = super.generateJsonSchema(new TypeReference<List<Record>>() {});
        String body = resourceToString("language-subtag-registry.rj", Charset.forName("UTF-8"), getClass().getClassLoader());
        Response response = given()
                .body(body)
                .queryParam("encoding", "UTF-8")
                .expect()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchema(schema))
                .when()
                .post(TEXT_PATH);
    }
}
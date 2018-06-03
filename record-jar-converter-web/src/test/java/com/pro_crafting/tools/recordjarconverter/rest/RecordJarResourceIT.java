package com.pro_crafting.tools.recordjarconverter.rest;

import com.pro_crafting.tools.recordjarconverter.RestApplication;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.*;
import org.xnio.IoUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.apache.commons.io.IOUtils.resourceToByteArray;
import static org.apache.commons.io.IOUtils.resourceToString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecordJarResourceIT {

    private static String MULTIPART_FORM_FILE_PATH = RestApplication.VERSION_PATH + RecordJarResource.RESOURCE_PATH + "multipart/file";
    private static String MULTIPART_FORM_TEXT_PATH = RestApplication.VERSION_PATH + RecordJarResource.RESOURCE_PATH + "multipart/text";
    private static String TEXT_PATH = RestApplication.VERSION_PATH + RecordJarResource.RESOURCE_PATH + "text";

    @BeforeAll
    static void beforeAll() {
        enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void testUploadMultipartFile() throws IOException {
        byte[] bytes = resourceToByteArray("taoup-rj-example.rj", getClass().getClassLoader());
        String expected = resourceToString("taoup-rj-converted.json", Charset.forName("UTF-8"), getClass().getClassLoader());
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
    @Disabled
    void testUploadMultipartFileLarge() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("language-subtag-registry.rj");
        Response response = given()
                .multiPart("file", "language-subtag-registry.rj", stream)
                .multiPart("encoding", "UTF-8")
                .expect()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .when()
                .post(MULTIPART_FORM_FILE_PATH);


        response.asString();
    }

    @Test
    void testUploadMultipartText() throws IOException {
        String body = resourceToString("taoup-rj-example.rj", Charset.forName("UTF-8"), getClass().getClassLoader());
        String expected = resourceToString("taoup-rj-converted.json", Charset.forName("UTF-8"), getClass().getClassLoader());
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
    void testUploadText() throws IOException {
        String body = resourceToString("taoup-rj-example.rj", Charset.forName("UTF-8"), getClass().getClassLoader());
        String expected = resourceToString("taoup-rj-converted.json", Charset.forName("UTF-8"), getClass().getClassLoader());
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
}
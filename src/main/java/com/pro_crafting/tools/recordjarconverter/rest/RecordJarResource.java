package com.pro_crafting.tools.recordjarconverter.rest;

import com.google.common.collect.Multimap;
import com.pro_crafting.tools.recordjarconverter.rest.model.RecordJarFile;
import com.pro_crafting.tools.recordjarconverter.rest.model.RecordJarText;
import com.pro_crafting.tools.recordjarconverter.service.RecordJarService;
import com.pro_crafting.tools.recordjarconverter.service.Violation;
import com.pro_crafting.tools.recordjarconverter.service.model.Record;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.reactive.MultipartForm;
import org.jboss.resteasy.reactive.RestResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@Path(RecordJarResource.RESOURCE_PATH)
@ApplicationScoped
public class RecordJarResource {
    public static final String RESOURCE_PATH = "record/jar/";

    @Inject
    RecordJarService service;

    @Context
    UriInfo uriInfo;

    @Context
    HttpHeaders headers;

    @POST
    @Path("multipart/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Converts a record-jar formatted file to JSON. Convenience method for file based conversion.")
    @APIResponses({
            @APIResponse(
                    responseCode = "200", description = "200 OK. Parsing of the file was okay, and the resulting JSON is in the response.", content = @Content(schema = @Schema(implementation = Map.class, type = SchemaType.ARRAY))
            ),
            @APIResponse(
                    responseCode = "400", description = "400 Bad Request. Violations are present in the body.", content = @Content(schema = @Schema(implementation = Violation.class, type = SchemaType.ARRAY))
            )
    })
    public RestResponse<List<Multimap<String, String>>> uploadMultipartFile(@MultipartForm RecordJarFile recordJarFile) throws FileNotFoundException {
        List<Record> records = service.convert(new FileInputStream(recordJarFile.getFile().uploadedFile().toFile()), recordJarFile.getEncoding());

        return RestResponse.ok(map(records));
    }

    @POST
    @Path("multipart/text")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Converts a record-jar formatted text to JSON. Convenience method for easy converting of text from html forms.")
    @APIResponses({
            @APIResponse(
                    responseCode = "200", description = "200 OK. Parsing of the file was okay, and the resulting JSON is in the response.", content = @Content(schema = @Schema(implementation = Map.class, type = SchemaType.ARRAY))
            ),
            @APIResponse(
                    responseCode = "400", description = "400 Bad Request. Violations are present in the body.", content = @Content(schema = @Schema(implementation = Violation.class, type = SchemaType.ARRAY))
            )
    })
    public RestResponse<List<Multimap<String, String>>> uploadMultipartText(@MultipartForm RecordJarText recordJarText) {
        if (recordJarText.getEncoding() == null) {
            recordJarText.setEncoding("UTF-8");
        }

        List<Record> records = service.convert(new ByteArrayInputStream(recordJarText.getText().getBytes(Charset.forName(recordJarText.getEncoding()))), recordJarText.getEncoding());

        return RestResponse.ok(map(records));
    }

    @POST
    @Path("text")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Converts a record-jar formatted text to JSON. Convenience method for easy converting of text.")
    @APIResponses({
            @APIResponse(
                    responseCode = "200", description = "200 OK. Parsing of the file was okay, and the resulting JSON is in the response.", content = @Content(schema = @Schema(implementation = Map.class, type = SchemaType.ARRAY))
            ),
            @APIResponse(
                    responseCode = "400", description = "400 Bad Request. Violations are present in the body.", content = @Content(schema = @Schema(implementation = Violation.class, type = SchemaType.ARRAY))
            )
    })
    public RestResponse<List<Multimap<String, String>>> uploadText(@Parameter(description = "Encoding of the specified record-jar formatted file.", example="UTF-8") @DefaultValue("UTF-8") @QueryParam("encoding") String encoding,
                               @RequestBody(description = "Record Jar formatted text", required = true, content = @Content(schema = @Schema(type = SchemaType.STRING))) byte[] recordJarText) {
        if (encoding == null) {
            encoding = "UTF-8";
        }

        List<Record> records = service.convert(new ByteArrayInputStream(recordJarText), encoding);

        return RestResponse.ok(map(records));
    }

    private List<Multimap<String, String>> map(List<Record> records) {
        if (records == null) {
            return Collections.emptyList();
        }
        return records.stream().map(Record::getFields).toList();
    }
}

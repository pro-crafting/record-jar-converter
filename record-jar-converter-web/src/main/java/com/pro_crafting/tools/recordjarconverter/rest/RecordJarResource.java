package com.pro_crafting.tools.recordjarconverter.rest;

import com.google.common.collect.Multimap;
import com.pro_crafting.tools.recordjarconverter.rest.model.RecordJarFile;
import com.pro_crafting.tools.recordjarconverter.rest.model.RecordJarText;
import com.pro_crafting.tools.recordjarconverter.service.RecordJarService;
import com.pro_crafting.tools.recordjarconverter.service.Violation;
import com.pro_crafting.tools.recordjarconverter.service.model.Record;
import io.swagger.annotations.*;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@Api(value = "Record Jar API")
@Path(RecordJarResource.RESOURCE_PATH)
@RequestScoped
public class RecordJarResource {
    public static final String RESOURCE_PATH = "record/jar/";

    @Inject
    RecordJarService service;

    @POST
    @Path("multipart/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Converts a record-jar formatted file to JSON. Convenience method for file based conversion.", response = Map.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(
                    code = 200, response = Map.class, responseContainer = "List", message = "200 OK. Parsing of the file was okay, and the resulting JSON is in the response."
            ),
            @ApiResponse(
                    code = 400, responseContainer = "Set", response = Violation.class, message = "400 Bad Request. Violations are present in the body."
            )
    })
    public Response uploadMultipartFile(@ApiParam @MultipartForm RecordJarFile recordJarFile) {
        List<Record> records = service.convert(recordJarFile.getFile(), recordJarFile.getEncoding());
        return Response.ok().entity(map(records)).build();
    }

    @POST
    @Path("multipart/text")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Converts a record-jar formatted text to JSON. Convenience method for easy converting of text from html forms.", response = Map.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(
                    code = 200, response = Map.class, responseContainer = "List", message = "200 OK. Parsing of the file was okay, and the resulting JSON is in the response."
            ),
            @ApiResponse(
                    code = 400, responseContainer = "Set", response = Violation.class, message = "400 Bad Request. Violations are present in the body."
            )
    })
    public Response uploadMultipartText(@ApiParam @MultipartForm RecordJarText recordJarText) {
        if (recordJarText.getEncoding() == null) {
            recordJarText.setEncoding("UTF-8");
        }

        List<Record> records = service.convert(new ByteArrayInputStream(recordJarText.getText().getBytes(Charset.forName(recordJarText.getEncoding()))), recordJarText.getEncoding());
        return Response.ok().entity(map(records)).build();
    }

    @POST
    @Path("text")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Converts a record-jar formatted text to JSON. Convenience method for easy converting of text.")
    @ApiResponses({
            @ApiResponse(
                    code = 200, response = Map.class, responseContainer = "List", message = "200 OK. Parsing of the file was okay, and the resulting JSON is in the response."
            ),
            @ApiResponse(
                    code = 400, responseContainer = "Set", response = Violation.class, message = "400 Bad Request. Violations are present in the body."
            )
    })
    public Response uploadText(@ApiParam(value = "Encoding of the specified record-jar formatted file.", example="UTF-8", defaultValue = "UTF-8") @QueryParam("encoding") String encoding,
                               @ApiParam(value = "Record Jar formatted text", required = true, type = "String") byte[] recordJarText) {
        if (encoding == null) {
            encoding = "UTF-8";
        }

        List<Record> records = service.convert(new ByteArrayInputStream(recordJarText), encoding);
        return Response.ok().entity(map(records)).build();
    }

    private List<Map<String, Object>> map(List<Record> records) {

        List<Map<String, Object>> recordsMap = new ArrayList<>();

        for (Record record : records) {
            Map<String, Object> recordMap = new HashMap<>();
            record.getFields().keySet().forEach((k) -> {
                Collection<String> values = record.getFields().get(k);
                if (values.size() == 1) {
                    recordMap.put(k, values.iterator().next());
                } else {
                    recordMap.put(k, values);
                }
            });
            recordsMap.add(recordMap);
        }

        return recordsMap;
    }
}

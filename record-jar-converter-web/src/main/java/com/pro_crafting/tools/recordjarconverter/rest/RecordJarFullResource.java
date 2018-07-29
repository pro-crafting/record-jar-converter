package com.pro_crafting.tools.recordjarconverter.rest;

import com.pro_crafting.tools.recordjarconverter.rest.model.RecordDocumentation;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.List;

@Api(value = "Record Jar API")
@Path(RecordJarFullResource.RESOURCE_PATH)
@RequestScoped
public class RecordJarFullResource {
    public static final String RESOURCE_PATH = "record/jar/full/";

    @Inject
    private RecordJarService service;

    @POST
    @Path("multipart/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Converts a record-jar formatted file to JSON. Comments are included. Convenience method for file based conversion.", response = RecordDocumentation.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(
                    code = 200, response = RecordDocumentation.class, responseContainer = "List", message = "200 OK. Parsing of the file was okay, and the resulting JSON is in the response."
            ),
            @ApiResponse(
                    code = 400, responseContainer = "Set", response = Violation.class, message = "400 Bad Request. Violations are present in the body."
            )
    })
    public Response uploadMultipartFile(@ApiParam @MultipartForm RecordJarFile recordJarFile) throws FileNotFoundException {
        List<Record> records = service.convert(new FileInputStream(recordJarFile.getFile()), recordJarFile.getEncoding());
        return Response.ok().entity(records).build();
    }

    @POST
    @Path("multipart/text")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Converts a record-jar formatted text to JSON. Comments are included. Convenience method for easy converting of text from html forms.", response = RecordDocumentation.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(
                    code = 200, response = RecordDocumentation.class, responseContainer = "List", message = "200 OK. Parsing of the file was okay, and the resulting JSON is in the response."
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
        return Response.ok().entity(records).build();
    }

    @POST
    @Path("text")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Converts a record-jar formatted text to JSON. Comments are included. Convenience method for easy converting of text.")
    @ApiResponses({
            @ApiResponse(
                    code = 200, response = RecordDocumentation.class, responseContainer = "List", message = "200 OK. Parsing of the file was okay, and the resulting JSON is in the response."
            ),
            @ApiResponse(
                    code = 400, responseContainer = "Set", response = Violation.class, message = "400 Bad Request. Violations are present in the body."
            )
    })
    public Response uploadText(@ApiParam(value = "Encoding of the specified record-jar formatted file.", example="UTF-8", defaultValue = "UTF-8") @QueryParam("encoding") String encoding,
                               @ApiParam(value = "RecordDocumentation Jar formatted text", required = true) byte[] recordJarText) {
        if (encoding == null) {
            encoding = "UTF-8";
        }

        List<Record> records = service.convert(new ByteArrayInputStream(recordJarText), encoding);
        return Response.ok().entity(records).build();
    }
}

package com.pro_crafting.tools.recordjarconverter.rest;

import com.pro_crafting.tools.recordjarconverter.rest.model.RecordJarFile;
import com.pro_crafting.tools.recordjarconverter.rest.model.RecordJarText;
import com.pro_crafting.tools.recordjarconverter.service.RecordJarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import sun.misc.IOUtils;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Api(value = "Record Jar API")
@Path(RecordJarResource.RESOURCE_PATH)
public class RecordJarResource {
    public static final String RESOURCE_PATH = "record/jar/";

    @Inject
    private RecordJarService service;

    @POST
    @Path("multipart/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Converts a record-jar formatted file to JSON. Convenience method for file based conversion.", response = Map.class, responseContainer = "List")
    public Response upload(@ApiParam @MultipartForm RecordJarFile recordJarFile) throws FileNotFoundException {
        List<Map<String, String>> records = service.convert(new FileInputStream(recordJarFile.getFile()), recordJarFile.getEncoding());
        return Response.ok().entity(records).build();
    }

    @POST
    @Path("multipart/text")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Converts a record-jar formatted text to JSON. Convenience method for easy converting of text from html forms.", response = Map.class, responseContainer = "List")
    public Response uploadFormText(@ApiParam @MultipartForm RecordJarText recordJarText) {
        if (recordJarText.getEncoding() == null) {
            recordJarText.setEncoding("UTF-8");
        }

        List<Map<String, String>> records = service.convert(new ByteArrayInputStream(recordJarText.getText().getBytes(Charset.forName(recordJarText.getEncoding()))), recordJarText.getEncoding());
        return Response.ok().entity(records).build();
    }

    @POST
    @Path("text")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Converts a record-jar formatted text to JSON. Convenience method for easy converting of text.", response = Map.class, responseContainer = "List")
    public Response uploadText(@ApiParam(value = "Encoding of the specified record-jar formatted file.", example="UTF-8", defaultValue = "UTF-8") @QueryParam("encoding") String encoding,
                               @ApiParam(value = "Record Jar formatted text", required = true) String recordJarText) {
        if (encoding == null) {
            encoding = "UTF-8";
        }

        List<Map<String, String>> records = service.convert(new ByteArrayInputStream(recordJarText.getBytes(Charset.forName(encoding))), encoding);
        return Response.ok().entity(records).build();
    }
}

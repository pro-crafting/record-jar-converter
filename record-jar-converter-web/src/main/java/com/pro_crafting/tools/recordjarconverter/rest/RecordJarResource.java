package com.pro_crafting.tools.recordjarconverter.rest;

import com.pro_crafting.tools.recordjarconverter.rest.model.RecordJarFile;
import com.pro_crafting.tools.recordjarconverter.service.RecordJarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import sun.misc.IOUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Api(value = "Record Jar API")
@Path(RecordJarResource.RESOURCE_PATH)
public class RecordJarResource {
    public static final String RESOURCE_PATH = "record/jar/";

    @Inject
    private RecordJarService service;

    @POST
    @Consumes("multipart/form-data")
    @Produces("application/json")
    @ApiOperation(value = "Converts a record-jar formatted file to json", response = Map.class, responseContainer = "List")
    public Response upload(@ApiParam @MultipartForm RecordJarFile recordJarFile) throws FileNotFoundException {
        List<Map<String, String>> records = service.convert(new FileInputStream(recordJarFile.getFile()), recordJarFile.getEncoding());
        return Response.ok().entity(records).build();
    }
}

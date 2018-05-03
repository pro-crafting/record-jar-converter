package com.pro_crafting.tools.recordjarconverter.rest;

import com.pro_crafting.tools.recordjarconverter.rest.model.RecordJarFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Api(value = "Record Jar API")
@Path(RecordJarResource.RESOURCE_PATH)
public class RecordJarResource {
    public static final String RESOURCE_PATH = "record/jar/";

    @POST
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public Response upload(@ApiParam @MultipartForm RecordJarFile recordJarFile) {

        return Response.ok().build();
    }
}

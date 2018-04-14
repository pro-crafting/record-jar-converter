package de.procrafting.tools.recordjarconverter.rest;

import de.procrafting.tools.recordjarconverter.rest.model.RecordJarFile;
import io.swagger.annotations.Api;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;

@Api
@Path(RecordJarResource.RESOURCE_PATH)
public class RecordJarResource {
    public static final String RESOURCE_PATH = "record/jar/";

    @POST
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public Response upload(@MultipartForm RecordJarFile recordJarFile) {

        return Response.ok().build();
    }
}

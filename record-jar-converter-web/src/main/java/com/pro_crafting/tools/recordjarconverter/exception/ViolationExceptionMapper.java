package com.pro_crafting.tools.recordjarconverter.exception;

import com.pro_crafting.tools.recordjarconverter.service.ViolationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ViolationExceptionMapper
                    implements ExceptionMapper<ViolationException> {
 
    @Override
    public Response toResponse(ViolationException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ex.getViolations()).build();
    }
}
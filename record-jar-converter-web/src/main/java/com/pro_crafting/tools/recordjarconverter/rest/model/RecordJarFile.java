package com.pro_crafting.tools.recordjarconverter.rest.model;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import java.io.File;

public class RecordJarFile {
    @FormParam("file")
    @PartType("application/octect-stream")
    private File file;

    @FormParam("encoding")
    @PartType("text/plain")
    private String encoding;
}

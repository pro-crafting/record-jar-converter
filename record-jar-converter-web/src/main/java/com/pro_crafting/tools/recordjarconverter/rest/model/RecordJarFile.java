package com.pro_crafting.tools.recordjarconverter.rest.model;

import io.swagger.annotations.ApiParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import java.io.File;
import java.io.InputStream;

public class RecordJarFile {
    @FormParam("file")
    @PartType("application/octect-stream")
    @ApiParam(value = "Record Jar formatted file", required = true)
    private InputStream file;

    @FormParam("encoding")
    @PartType("text/plain")
    @ApiParam(value = "Encoding of the specified record-jar formatted file.", example="UTF-8", defaultValue = "UTF-8")
    private String encoding;

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}

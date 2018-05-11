package com.pro_crafting.tools.recordjarconverter.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
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

    @ApiParam(value = "Record Jar formatted file", required = true)
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @ApiParam(value = "Encoding of the specified record-jar formatted file.", example="UTF-8", defaultValue = "UTF-8")
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}

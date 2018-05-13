package com.pro_crafting.tools.recordjarconverter.rest.model;

import io.swagger.annotations.ApiParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;

public class RecordJarText {
    @FormParam("text")
    @PartType("text/plain")
    private String text;

    @FormParam("encoding")
    @PartType("text/plain")
    private String encoding;

    @ApiParam(value = "Record Jar formatted text", required = true)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ApiParam(value = "Encoding of the specified record-jar formatted file.", example="UTF-8", defaultValue = "UTF-8")
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}

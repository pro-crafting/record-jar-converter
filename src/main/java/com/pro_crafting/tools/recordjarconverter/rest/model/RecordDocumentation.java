package com.pro_crafting.tools.recordjarconverter.rest.model;

import com.google.common.collect.Multimap;
import com.pro_crafting.tools.recordjarconverter.service.model.Record;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Map;

public abstract class RecordDocumentation extends Record {
    @Schema(description = "A key value pair read from the record jar file.", example = "Moons: Luna", required = true)
    @Override
    public abstract Multimap<String, String> getFields();

    @Schema(description = "Comments that immediately preceded the record. This is basicly a list consisting of free text.")
    @Override
    public abstract String[] getComments();

    @Schema(hidden = true)
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}

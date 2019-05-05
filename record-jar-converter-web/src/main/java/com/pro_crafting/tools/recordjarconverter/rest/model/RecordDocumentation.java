package com.pro_crafting.tools.recordjarconverter.rest.model;

import com.google.common.collect.Multimap;
import com.pro_crafting.tools.recordjarconverter.service.model.Record;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@ApiModel("Record")
public abstract class RecordDocumentation extends Record {
    @ApiModelProperty(value = "A key value pair read from the record jar file.", example= "Moons: Luna", required = true)
    @Override
    public abstract Multimap<String, String> getFields();

    @ApiModelProperty(value = "Comments that immediately preceded the record. This is basicly a list consisting of free text.")
    @Override
    public abstract String[] getComments();

    @ApiModelProperty(hidden = true)
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}

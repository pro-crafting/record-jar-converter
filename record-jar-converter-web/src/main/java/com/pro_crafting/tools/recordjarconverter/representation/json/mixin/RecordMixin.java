package com.pro_crafting.tools.recordjarconverter.representation.json.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pro_crafting.tools.recordjarconverter.service.model.Record;

public abstract class RecordMixin extends Record {
    @JsonIgnore
    @Override
    public abstract boolean isEmpty();
}

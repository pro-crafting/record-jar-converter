package com.pro_crafting.tools.recordjarconverter.representation.json.module;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pro_crafting.tools.recordjarconverter.representation.json.mixin.RecordMixin;
import com.pro_crafting.tools.recordjarconverter.service.model.Record;

public class RecordModule extends SimpleModule {
    public RecordModule() {
        // TODO: get version number from
        super("RecordModule", new Version(1,0,0, null, null, null));
        setMixInAnnotation(Record.class, RecordMixin.class);
    }
}

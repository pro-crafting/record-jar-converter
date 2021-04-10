package com.pro_crafting.tools.recordjarconverter.representation.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.quarkus.jackson.ObjectMapperCustomizer;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObjectMapperProvider implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        // Custom serializers
        SimpleModule serializerModule = new SimpleModule();
        serializerModule.addSerializer(new MultimapSerializer());
        objectMapper.registerModule(serializerModule);
    }
}

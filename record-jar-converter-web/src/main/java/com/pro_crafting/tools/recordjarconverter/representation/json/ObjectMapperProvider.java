package com.pro_crafting.tools.recordjarconverter.representation.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.google.common.collect.Multimap;
import com.pro_crafting.tools.recordjarconverter.representation.json.module.RecordModule;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
@Consumes(MediaType.WILDCARD)
@Produces(MediaType.WILDCARD)
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.registerModule(new RecordModule());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        // Custom serializers
        SimpleModule serializerModule = new SimpleModule();
        serializerModule.addSerializer(new MultimapSerializer());

        mapper.registerModule(serializerModule);
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}

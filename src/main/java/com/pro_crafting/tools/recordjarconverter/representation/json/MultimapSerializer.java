package com.pro_crafting.tools.recordjarconverter.representation.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.util.Collection;

/**
 * Custom multimap serializer. If only one value for a given key exists, the value itself is written. if multiple values exist, an array is written
 */
public class MultimapSerializer extends StdSerializer<Multimap<Object, Object>> {

    protected MultimapSerializer() {
        super(TypeFactory.defaultInstance().constructType(Multimap.class));
    }

    @Override
    public void serialize(Multimap<Object, Object> map, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (map == null || map.isEmpty()) {
            return;
        }

        gen.writeStartObject();

        for (Object k : map.keySet()) {
            Collection<Object> values = map.get(k);

            if (values.isEmpty()) {
                continue;
            }

            if (values.size() == 1) {
                gen.writeStringField(k.toString(), values.iterator().next().toString());
            } else {
                gen.writeArrayFieldStart(k.toString());
                for (Object value : values) {
                    gen.writeString(value.toString());
                }
                gen.writeEndArray();
            }
        }

        gen.writeEndObject();
    }
}
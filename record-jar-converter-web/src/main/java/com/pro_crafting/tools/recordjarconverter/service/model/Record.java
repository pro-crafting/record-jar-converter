package com.pro_crafting.tools.recordjarconverter.service.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import java.util.*;

public class Record {
    private Multimap<String, String> fields;
    private String[] comments;

    public Record() {
        this.fields = MultimapBuilder.linkedHashKeys().arrayListValues().build();
    }

    public Record(Record record) {
        this(record.getFields(), record.getComments());
    }

    public Record(Multimap<String, String> fields, String[] comments) {
        this.fields = MultimapBuilder.linkedHashKeys().arrayListValues().build(fields);
        this.comments = comments;
    }

    public Multimap<String, String> getFields() {
        return fields;
    }

    /**
     * Returns the comments of this record
     * @return arrays of line, may be null
     */
    public String[] getComments() {
        return comments;
    }

    public void setComments(String[] comments) {
        this.comments = comments;
    }

    public void clear() {
        this.fields.clear();
        comments = null;
    }

    public boolean isEmpty() {
        return this.fields.isEmpty();
    }
}

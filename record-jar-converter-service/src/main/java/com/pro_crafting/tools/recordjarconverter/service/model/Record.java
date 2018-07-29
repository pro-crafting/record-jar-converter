package com.pro_crafting.tools.recordjarconverter.service.model;

import java.util.*;

public class Record {
    private Map<String, String> fields;
    private String[] comments;

    public Record() {
        this.fields = new HashMap<>();
    }

    public Record(Record record) {
        this(record.getFields(), record.getComments());
    }

    public Record(Map<String, String> fields, String[] comments) {
        this.fields = new HashMap<>(fields);
        this.comments = comments;
    }

    public Map<String, String> getFields() {
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

package com.pro_crafting.tools.recordjarconverter.service.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Record {
    private Map<String, String> fields = new HashMap<>();
    private List<String> comments = new ArrayList<>();

    public Map<String, String> getFields() {
        return fields;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        if (comments == null) {
            this.comments.clear();
            return;
        }
        this.comments = comments;
    }

    public void clear() {
        this.fields.clear();
        this.comments.clear();
    }

    public boolean isEmpty() {
        return this.fields.isEmpty();
    }

    public static Record of(Map<String, String> fields, List<String> comments) {
        Record record = new Record();
        record.fields = Maps.newHashMap(fields);
        record.comments = Lists.newArrayList(comments);
        return record;
    }

    public static Record of(Record record) {
        return of(record.fields, record.comments);
    }
}

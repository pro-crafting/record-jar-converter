package com.pro_crafting.tools.recordjarconverter.service.model;

import java.util.List;

public class RecordList {
    private List<Record> records;

    public RecordList(List<Record> records) {
        this.records = records;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}

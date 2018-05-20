package com.pro_crafting.tools.recordjarconverter.service;

public class Violation {
    private int lineNumber;

    public Violation(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}

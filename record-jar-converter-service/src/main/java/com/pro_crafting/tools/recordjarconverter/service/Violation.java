package com.pro_crafting.tools.recordjarconverter.service;

public class Violation {
    private final String line;
    private final String errorCode;
    private final String message;
    private final int row;
    private final int column;

    public Violation(String line, String errorCode, String message, int row, int column) {
        this.line = line;
        this.errorCode = errorCode;
        this.message = message;
        this.row = row;
        this.column = column;
    }

    public String getLine() {
        return line;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getMessage() {
        return message;
    }
}

package com.pro_crafting.tools.recordjarconverter.service;

public class ErrorCode {
    private ErrorCode() {

    }

    // Warnings
    public static final String WARNING_SUCCESSIVE_LINE_NO_LEADING_WHITESPACE = "WARNING_SUCCESSIVE_LINE_NO_LEADING_WHITESPACE";

    // Errors
    public static final String ERROR_FIELD_NO_NAME_OR_NO_BODY = "ERROR_FIELD_NO_NAME_OR_NO_BODY";
    public static final String ERROR_FIELD_NAME_INVALID = "ERROR_FIELD_NAME_INVALID";
    public static final String ERROR_FIELD_EMPTY_BODY = "ERROR_FIELD_EMPTY_BODY";
}

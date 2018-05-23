package com.pro_crafting.tools.recordjarconverter.service;

import com.google.common.collect.Sets;

import javax.enterprise.context.RequestScoped;
import java.util.ResourceBundle;
import java.util.Set;

@RequestScoped
public class DecoderContext {
    private Set<Violation> violations = Sets.newHashSet();
    private int lineNumber;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("violations");

    public void addViolation(String line, String errorCode, int row) {
        this.addViolation(line, errorCode, row, 0);
    }

    public void addViolation(String line, String errorCode, int row, int column) {
        String message = resourceBundle.getString(errorCode);
        this.violations.add(new Violation(line, errorCode, message, row, column));
    }

    /**
     * Get all occured violations. To add violations, use {@link #addViolation(String, String, int)}
     * @return all occured violations as a set. Changes to this set will not be applied to the violations in the DecoderContext.
     */
    public Set<Violation> getViolations() {
        return Sets.newHashSet(violations);
    }

    /**
     * Get the currently being read, or last read line number.
     *
     * @return lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * {@link #getLineNumber()}
     * @param lineNumber current line number
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}

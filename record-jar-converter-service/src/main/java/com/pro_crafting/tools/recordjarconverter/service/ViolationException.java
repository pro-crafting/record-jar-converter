package com.pro_crafting.tools.recordjarconverter.service;

import java.util.ArrayList;
import java.util.Collection;

public class ViolationException extends RuntimeException {
    private final transient Collection<Violation> violations;

    public ViolationException() {
        this(new ArrayList<>());
    }

    public ViolationException(Collection<Violation> violations) {
        this.violations = violations;
    }

    public Collection<Violation> getViolations() {
        return violations;
    }
}

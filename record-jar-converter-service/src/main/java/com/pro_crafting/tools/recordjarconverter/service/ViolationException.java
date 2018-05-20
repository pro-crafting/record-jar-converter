package com.pro_crafting.tools.recordjarconverter.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ViolationException extends RuntimeException {
    private Collection<Violation> violations = new ArrayList<>();

    public ViolationException(Collection<Violation> violations) {
        this.violations = violations;
    }

    public Collection<Violation> getViolations() {
        return violations;
    }

    public void setViolations(Collection<Violation> violations) {
        this.violations = violations;
    }
}

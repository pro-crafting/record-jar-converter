package com.pro_crafting.tools.recordjarconverter.rest;

import com.pro_crafting.tools.recordjarconverter.RestApplication;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Readiness
@ApplicationScoped
public class RecordJarHealth implements HealthCheck {

    @Inject
    RecordJarResource resource;

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse
                .named(RestApplication.VERSION_PATH + RecordJarResource.RESOURCE_PATH)
                .status(resource != null)
                .build();
    }
}

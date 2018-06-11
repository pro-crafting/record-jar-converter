package com.pro_crafting.tools.recordjarconverter.rest;

import com.pro_crafting.tools.recordjarconverter.RestApplication;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class RecordJarHealth implements HealthCheck {

    @Inject
    private RecordJarResource resource;

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse
                .named(RestApplication.VERSION_PATH + RecordJarResource.RESOURCE_PATH)
                .state(resource != null)
                .build();
    }
}

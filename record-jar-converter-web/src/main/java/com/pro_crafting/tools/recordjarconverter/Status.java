package com.pro_crafting.tools.recordjarconverter;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class Status implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("record-jar-converter-web").up().build();
    }
}

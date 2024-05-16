package com.intuit.craft.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import com.intuit.craft.service.impl.S3ServiceImpl;

@Component
public class S3HealthCheck extends AbstractHealthIndicator {

    private S3ServiceImpl s3Service;

    public S3HealthCheck(S3ServiceImpl s3Service) {
        super("S3 health check failed");
        this.s3Service = s3Service;
    }

    @Override
    protected void doHealthCheck(Health.Builder bldr) throws Exception {
        boolean running = s3Service.isBucketAccessible();
        if (running) {
            bldr.up();
        } else {
            bldr.down();
        }
    }
}

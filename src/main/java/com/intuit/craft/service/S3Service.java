package com.intuit.craft.service;

import java.util.concurrent.TimeoutException;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.intuit.craft.model.ObjectMetadata;

public interface S3Service {

    @Cacheable("s3ObjectMetadata")
    @Retryable(retryFor = { TimeoutException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    ObjectMetadata getObjectMetadata(String keyName, String userId);

    @Retryable(retryFor = { TimeoutException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    Boolean isBucketAccessible();

}

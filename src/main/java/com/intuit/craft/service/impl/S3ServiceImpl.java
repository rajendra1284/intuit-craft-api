package com.intuit.craft.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.intuit.craft.model.ObjectMetadata;
import com.intuit.craft.service.S3Service;
import com.intuit.craft.util.DateUtil;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectAttributesRequest;
import software.amazon.awssdk.services.s3.model.GetObjectAttributesResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.ObjectAttributes;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3ServiceImpl implements S3Service {

    private S3Client s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    private static final Logger LOGGER = LoggerFactory.getLogger(S3ServiceImpl.class);

    public S3ServiceImpl(S3Client s3client) {
        this.s3client = s3client;
    }

    /**
     * Get S3 Object Metadata
     * 
     * @param keyName
     * @return ObjectMetadata
     */
    @Override
    public ObjectMetadata getObjectMetadata(String keyName, String userId) {
        ObjectMetadata metadata = new ObjectMetadata();
        GetObjectAttributesRequest request = GetObjectAttributesRequest.builder().bucket(bucketName).key(keyName)
                .objectAttributes(ObjectAttributes.knownValues()).build();

        GetObjectAttributesResponse response = s3client.getObjectAttributes(request);
        String lastModifiedDate = DateUtil.formatDateTime(response.lastModified());
        metadata.setLastModifiedDate(lastModifiedDate);

        metadata.setStorageClass(response.storageClassAsString());
        metadata.setVersionId(response.versionId());
        if (response.objectParts() != null) {
            metadata.setPartsCount(response.objectParts().totalPartsCount());
        }

        return metadata;
    }

    /**
     * Check if the bucket accessible or not.
     * 
     * @return Boolean
     */
    @Override
    public Boolean isBucketAccessible() {
        try {
            HeadBucketRequest request = HeadBucketRequest.builder().bucket(bucketName).build();
            HeadBucketResponse response = s3client.headBucket(request);
            return response != null;
        } catch (S3Exception ex) {
            LOGGER.error("S3 Bucket: " + bucketName + " is not accessible.", ex);
            return false;
        }
    }

}

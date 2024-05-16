package com.intuit.craft.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intuit.craft.TestApplication;
import com.intuit.craft.domain.AuditLog;
import com.intuit.craft.model.AuditLogDto;
import com.intuit.craft.model.ObjectMetadata;
import com.intuit.craft.repository.AuditLogRepository;
import com.intuit.craft.util.JSONUtil;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectAttributesRequest;
import software.amazon.awssdk.services.s3.model.GetObjectAttributesResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.S3Exception;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(classes = TestApplication.class)
public class IntuitCraftApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private S3Client s3Client;

    @Autowired
    CacheManager cacheManager;
    
    @Autowired
    private AuditLogRepository repository;

    @Test
    @WithMockUser("test_user1")
    void testUnAuthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/v1/object_metadata/test_file").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test_user2", roles = { "user" })
    void testObjectMetadataApi() throws Exception {
        GetObjectAttributesResponse s3Resp = mock(GetObjectAttributesResponse.class);
        when(s3Resp.lastModified()).thenReturn(Instant.now());
        when(s3Resp.storageClassAsString()).thenReturn("testStorageClass");
        when(s3Resp.versionId()).thenReturn("testVersionId");
        when(s3Client.getObjectAttributes(any(GetObjectAttributesRequest.class))).thenReturn(s3Resp);

        MvcResult result = mockMvc
                .perform(get("/api/v1/object_metadata/test_file1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String responseData = result.getResponse().getContentAsString();
        ObjectMetadata metadata = JSONUtil.deserialize(responseData, ObjectMetadata.class);
        Assertions.assertEquals("testVersionId", metadata.getVersionId());
        Assertions.assertEquals("testStorageClass", metadata.getStorageClass());

    }

    @Test
    @WithMockUser(username = "test_user3", roles = { "user" })
    void testUnauthorizedAuditLogsAccess() throws Exception {
        mockMvc.perform(get("/api/v1/audit_log").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test_user4", roles = { "admin" })
    void testAuditLogs() throws Exception {
        List<AuditLog> logs = new ArrayList<>();
        AuditLog auditLog = new AuditLog();
        auditLog.setUserId("testUserId");
        auditLog.setObjectName("testObjectName");
        auditLog.setCreateTs(new Timestamp(System.currentTimeMillis()));
        logs.add(auditLog);
        when(repository.findAll()).thenReturn(logs);

        MvcResult result = mockMvc.perform(get("/api/v1/audit_log").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String responseData = result.getResponse().getContentAsString();
        List<AuditLogDto> respLogs = JSONUtil.deserialize(responseData, new TypeReference<List<AuditLogDto>>() {
        });
        Assertions.assertEquals(1, respLogs.size());
        Assertions.assertEquals("testUserId", respLogs.get(0).getUserId());
        Assertions.assertEquals("testObjectName", respLogs.get(0).getObjectName());
    }

    @Test
    @WithMockUser(username = "test_user5", roles = { "user" })
    void testS3FileNotFound() throws Exception {
        when(s3Client.getObjectAttributes(any(GetObjectAttributesRequest.class))).thenThrow(NoSuchKeyException.class);
        mockMvc.perform(get("/api/v1/object_metadata/test_file2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test_user6", roles = { "user" })
    void testS3InternalServerError() throws Exception {
        when(s3Client.getObjectAttributes(any(GetObjectAttributesRequest.class))).thenThrow(S3Exception.class);
        mockMvc.perform(get("/api/v1/object_metadata/test_file3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "test_user7", roles = { "user" })
    void testEmptyObjectName() throws Exception {
        when(s3Client.getObjectAttributes(any(GetObjectAttributesRequest.class))).thenThrow(S3Exception.class);
        mockMvc.perform(get("/api/v1/object_metadata/ ").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

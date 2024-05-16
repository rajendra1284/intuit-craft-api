package com.intuit.craft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import com.intuit.craft.repository.AuditLogRepository;

import software.amazon.awssdk.services.s3.S3Client;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class TestApplication {

    @MockBean
    private S3Client s3Client;

    @MockBean
    private JwtDecoder jwtDecoder;

    @MockBean
    private AuditLogRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}

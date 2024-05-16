package com.intuit.craft.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.intuit.craft.model.StatusDto;

import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.S3Exception;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoSuchBucketException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public StatusDto handleException(NoSuchBucketException ex) {
        String message = "Bucket not found: " + ex.getMessage();
        StatusDto response = new StatusDto(message, HttpStatus.NOT_FOUND);
        LOGGER.error(message, ex);
        return response;
    }

    @ExceptionHandler(NoSuchKeyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public StatusDto handleException(NoSuchKeyException ex) {
        String message = "Object not found: " + ex.getMessage();
        StatusDto response = new StatusDto(message, HttpStatus.NOT_FOUND);
        LOGGER.error(message, ex);
        return response;
    }
    
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public StatusDto handleException(NoResourceFoundException ex) {
        String message = "Resource not found: " + ex.getMessage();
        StatusDto response = new StatusDto(message, HttpStatus.NOT_FOUND);
        LOGGER.error(message, ex);
        return response;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public StatusDto handleException(AccessDeniedException ex) {
        String message = ex.getMessage();
        StatusDto response = new StatusDto(message, HttpStatus.UNAUTHORIZED);
        LOGGER.error(message, ex);
        return response;
    }
    
    @ExceptionHandler(InvalidBearerTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public StatusDto handleException(InvalidBearerTokenException ex) {
        String message = "Bearer token invalid or malformed." + ex.getMessage();
        StatusDto response = new StatusDto(message, HttpStatus.UNAUTHORIZED);
        LOGGER.error(message, ex);
        return response;
    }
    
    @ExceptionHandler(S3Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public StatusDto handleException(S3Exception ex) {
        String message = "S3 Error: " + ex.getMessage();
        StatusDto response = new StatusDto(message, ex);
        LOGGER.error(message, ex);
        return response;
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public StatusDto handleException(DataAccessException ex) {
        String message = "Data access failed: " + ex.getMessage();
        StatusDto response = new StatusDto(message, ex);
        LOGGER.error(message, ex);
        return response;
    }

    @ExceptionHandler(CraftException.class)
    public ResponseEntity<StatusDto> handleException(CraftException ex) {
        return new ResponseEntity<StatusDto>(ex.getStatusDto(), ex.getStatusDto().getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public StatusDto handleException(Exception ex) {
        String message = "An unexpected error occurred: " + ex.getMessage();
        StatusDto response = new StatusDto(message, ex);
        LOGGER.error(message, ex);
        return response;
    }
}

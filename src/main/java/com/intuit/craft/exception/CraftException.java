package com.intuit.craft.exception;

import org.springframework.http.HttpStatus;

import com.intuit.craft.model.StatusDto;

/**
 * Custom exception
 */
public class CraftException extends RuntimeException {

    private StatusDto statusDto;

    private static final long serialVersionUID = -7036912357858562991L;

    public CraftException(String message) {
        super(message);
        statusDto = new StatusDto(message);
    }

    public CraftException(String message, HttpStatus statusCode) {
        super(message);
        statusDto = new StatusDto(message, statusCode);
    }

    public CraftException(String message, Throwable cause) {
        super(message, cause);
        statusDto = new StatusDto(message, cause);
    }

    public StatusDto getStatusDto() {
        return statusDto;
    }

}

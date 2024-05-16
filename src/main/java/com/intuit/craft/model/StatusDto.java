package com.intuit.craft.model;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDto {

    private String message;

    private String stackTrace;

    private HttpStatus statusCode;

    public StatusDto(String message) {
        this.message = message;
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public StatusDto(String message, Throwable ex) {
        this.message = message;
        this.stackTrace = ExceptionUtils.getStackTrace(ex);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public StatusDto(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the stackTrace
     */
    public String getStackTrace() {
        return stackTrace;
    }

    /**
     * @param stackTrace the stackTrace to set
     */
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    /**
     * @return the statusCode
     */
    public HttpStatus getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

}

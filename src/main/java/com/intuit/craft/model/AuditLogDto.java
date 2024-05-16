package com.intuit.craft.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditLogDto {

    private String userId;
    private String objectName;
    private String requestedDate;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the objectName
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * @param objectName the objectName to set
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * @return the requestedDate
     */
    public String getRequestedDate() {
        return requestedDate;
    }

    /**
     * @param requestedDate the requestedDate to set
     */
    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

}

package com.intuit.craft.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObjectMetadata {

    private String versionId;
    private String storageClass;
    private Integer partsCount = 0;
    private String lastModifiedDate;

    /**
     * @return the versionId
     */
    public String getVersionId() {
        return versionId;
    }

    /**
     * @param versionId the versionId to set
     */
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    /**
     * @return the storageClass
     */
    public String getStorageClass() {
        return storageClass;
    }

    /**
     * @param storageClass the storageClass to set
     */
    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }

    /**
     * @return the partsCount
     */
    public Integer getPartsCount() {
        return partsCount;
    }

    /**
     * @param partsCount the partsCount to set
     */
    public void setPartsCount(Integer partsCount) {
        this.partsCount = partsCount;
    }

    /**
     * @return the lastModifiedDate
     */
    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * @param lastModifiedDate the lastModifiedDate to set
     */
    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}

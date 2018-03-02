package com.google.address;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Abhishek Somani
 */
public class GoogleResponse {
    private Result[] results ;
    private String status ;
    private String error_message;

    public Result[] getResults() {
        return results;
    }
    public void setResults(Result[] results) {
        this.results = results;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }




}

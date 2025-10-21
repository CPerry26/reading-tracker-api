package com.codyperry.reading_tracker.dto;

/**
 * This is explicitly not a record to avoid the immutability those provide. Since I wanted to allow passing in
 * two different parameter types (pages read or percent complete), I used a regular class and did not implement
 * setters to achieve the same general idea.
 */
public class UpdateProgressRequest {
    private Integer pagesRead;
    private Double percentComplete;

    public UpdateProgressRequest(int pagesRead) {
        this.pagesRead = pagesRead;
    }

    public UpdateProgressRequest(double percentComplete) {
        this.percentComplete = percentComplete;
    }

    public Integer getPagesRead() {
        return pagesRead;
    }

    public Double getPercentComplete() {
        return percentComplete;
    }
}

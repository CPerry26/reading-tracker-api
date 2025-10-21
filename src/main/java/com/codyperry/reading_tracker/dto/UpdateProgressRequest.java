package com.codyperry.reading_tracker.dto;

/**
 * This is explicitly not a record to avoid the immutability those provide. Since I wanted to allow passing in
 * two different parameter types (pages read or percent complete), I used a regular class and did not implement
 * setters to achieve the same general idea.
 */
public class UpdateProgressRequest {
    private long bookId;
    private int pagesRead;
    private double percentComplete;

    public UpdateProgressRequest(long id, int pagesRead) {
        this.bookId = id;
        this.pagesRead = pagesRead;
    }

    public UpdateProgressRequest(long id, double percentComplete) {
        this.bookId = id;
        this.percentComplete = percentComplete;
    }

    public long getBookId() {
        return bookId;
    }

    public int getPagesRead() {
        return pagesRead;
    }

    public double getPercentComplete() {
        return percentComplete;
    }
}

package com.codyperry.reading_tracker.dto;

import java.util.Optional;

/**
 * This is explicitly not a record to avoid the immutability those provide. Since I wanted to allow passing in
 * two different parameter types (pages read or percent complete), I used a regular class and did not implement
 * setters to achieve the same general idea.
 */
public class UpdateProgressRequest {
    private Optional<Integer> pagesRead;
    private Optional<Double> percentComplete;

    public UpdateProgressRequest() {
        this.pagesRead = Optional.empty();
        this.percentComplete = Optional.empty();
    }

    public Integer setPagesRead(int pagesRead) {
        this.pagesRead = Optional.of(pagesRead);

        return pagesRead;
    }

    public Double setPercentComplete(double percentComplete) {
        this.percentComplete = Optional.of(percentComplete);

        return percentComplete;
    }

    public Optional<Integer> getPagesRead() {
        return pagesRead;
    }

    public Optional<Double> getPercentComplete() {
        return percentComplete;
    }
}

package com.codyperry.reading_tracker.service;

import com.codyperry.reading_tracker.dto.CreateBookRequest;
import com.codyperry.reading_tracker.dto.CreateBookResponse;
import com.codyperry.reading_tracker.dto.UpdateProgressRequest;

public class ReadingTrackerServiceImpl implements ReadingTrackerService {
    @Override
    public CreateBookResponse createNewBook(CreateBookRequest createBookRequest) {
        return null;
    }

    @Override
    public void getTrackedBooks() {

    }

    @Override
    public void getReadingStats() {

    }

    @Override
    public void updateTrackedProgress(long bookId, UpdateProgressRequest updateProgressRequest) {

    }
}

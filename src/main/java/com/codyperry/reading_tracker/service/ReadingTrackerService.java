package com.codyperry.reading_tracker.service;

import com.codyperry.reading_tracker.dto.CreateBookRequest;
import com.codyperry.reading_tracker.dto.CreateBookResponse;
import com.codyperry.reading_tracker.dto.UpdateProgressRequest;
import org.springframework.stereotype.Service;

@Service
public interface ReadingTrackerService {
    CreateBookResponse createNewBook(CreateBookRequest newBook);

    void getTrackedBooks();

    void getReadingStats();

    void updateTrackedProgress(long bookId, UpdateProgressRequest updateProgressRequest);
}

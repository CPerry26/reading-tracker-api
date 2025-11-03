package com.codyperry.reading_tracker.service;

import com.codyperry.reading_tracker.dto.BookDTO;
import com.codyperry.reading_tracker.dto.CreateBookRequest;
import com.codyperry.reading_tracker.dto.UpdateProgressRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReadingTrackerService {
    BookDTO createNewBook(CreateBookRequest newBook);

    List<BookDTO> getTrackedBooks();

    List<BookDTO> getReadingStats();

    Optional<BookDTO> updateTrackedProgress(long bookId, UpdateProgressRequest updateProgressRequest);
}

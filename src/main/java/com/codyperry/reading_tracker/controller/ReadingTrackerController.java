package com.codyperry.reading_tracker.controller;

import com.codyperry.reading_tracker.dto.CreateBookRequest;
import com.codyperry.reading_tracker.dto.CreateBookResponse;
import com.codyperry.reading_tracker.dto.UpdateProgressRequest;
import com.codyperry.reading_tracker.service.ReadingTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class ReadingTrackerController {
    private final ReadingTrackerService readingTrackerService;

    public ReadingTrackerController(@Autowired ReadingTrackerService readingTrackerService) {
        this.readingTrackerService = readingTrackerService;
    }

    @PostMapping
    public CreateBookResponse createTrackedBook(@RequestBody CreateBookRequest newBook) {
        return this.readingTrackerService.createNewBook(newBook);
    }

    @GetMapping
    public void getTrackedBooks() {
        this.readingTrackerService.getTrackedBooks();
    }

    @GetMapping("/stats")
    public void getReadingStats() {
        this.readingTrackerService.getReadingStats();
    }

    @PatchMapping("/{id}")
    public void updateProgress(@PathVariable long bookId, @RequestBody UpdateProgressRequest updateProgressRequest) {
        this.updateProgress(bookId, updateProgressRequest);
    }
}

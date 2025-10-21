package com.codyperry.reading_tracker.controller;

import com.codyperry.reading_tracker.dto.BookDTO;
import com.codyperry.reading_tracker.dto.CreateBookRequest;
import com.codyperry.reading_tracker.dto.CreateBookResponse;
import com.codyperry.reading_tracker.dto.UpdateProgressRequest;
import com.codyperry.reading_tracker.dto.UpdateProgressResponse;
import com.codyperry.reading_tracker.service.ReadingTrackerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class ReadingTrackerController {
    private final ReadingTrackerService readingTrackerService;

    public ReadingTrackerController(ReadingTrackerService readingTrackerService) {
        this.readingTrackerService = readingTrackerService;
    }

    @PostMapping
    public CreateBookResponse createTrackedBook(@RequestBody CreateBookRequest newBook) {
        BookDTO bookDTO = this.readingTrackerService.createNewBook(newBook);

        return new CreateBookResponse(bookDTO.id(), bookDTO.name(), bookDTO.author(), bookDTO.pages());
    }

    @GetMapping
    public List<BookDTO> getTrackedBooks() {
        return this.readingTrackerService.getTrackedBooks();
    }

    @GetMapping("/stats")
    public List<BookDTO> getReadingStats() {
        return this.readingTrackerService.getReadingStats();
    }

    @PatchMapping("/{id}")
    public UpdateProgressResponse updateProgress(@PathVariable long bookId, @RequestBody UpdateProgressRequest updateProgressRequest) {
        BookDTO bookDTO = this.readingTrackerService.updateTrackedProgress(bookId, updateProgressRequest);

        return new UpdateProgressResponse(
                bookDTO.id(),
                bookDTO.name(),
                bookDTO.author(),
                (double) bookDTO.pagesRead() / bookDTO.pages()
        );
    }
}

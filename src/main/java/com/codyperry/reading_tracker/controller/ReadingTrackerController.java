package com.codyperry.reading_tracker.controller;

import com.codyperry.reading_tracker.dto.BookDTO;
import com.codyperry.reading_tracker.dto.CreateBookRequest;
import com.codyperry.reading_tracker.dto.CreateBookResponse;
import com.codyperry.reading_tracker.dto.UpdateProgressRequest;
import com.codyperry.reading_tracker.dto.UpdateProgressResponse;
import com.codyperry.reading_tracker.service.ReadingTrackerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class ReadingTrackerController {
    private final ReadingTrackerService readingTrackerService;

    public ReadingTrackerController(ReadingTrackerService readingTrackerService) {
        this.readingTrackerService = readingTrackerService;
    }

    @PostMapping
    public CreateBookResponse createTrackedBook(@Valid @RequestBody CreateBookRequest newBook) {
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

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateProgressResponse> updateProgress(@Min(1) @PathVariable(name = "id") long bookId, @RequestBody UpdateProgressRequest updateProgressRequest) {
        if (!updateProgressRequest.getPagesRead().isPresent() && !updateProgressRequest.getPercentComplete().isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<BookDTO> bookDTO = this.readingTrackerService.updateTrackedProgress(bookId, updateProgressRequest);

        if (!bookDTO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookDTO bookProgress = bookDTO.get();

        // Convert the pages read into a whole number percentage.
        double percent = (Double.valueOf(bookProgress.pagesRead()) / Double.valueOf(bookProgress.pages()));
        int percentComplete = (int) (percent * 100);

        return new ResponseEntity<>(new UpdateProgressResponse(
                bookProgress.id(),
                bookProgress.name(),
                bookProgress.author(),
                percentComplete
        ), HttpStatus.OK);
    }
}

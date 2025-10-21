package com.codyperry.reading_tracker.service;

import com.codyperry.reading_tracker.dto.BookDTO;
import com.codyperry.reading_tracker.dto.CreateBookRequest;
import com.codyperry.reading_tracker.dto.UpdateProgressRequest;
import com.codyperry.reading_tracker.entity.Book;
import com.codyperry.reading_tracker.repository.TrackerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReadingTrackerServiceImpl implements ReadingTrackerService {
    private final TrackerRepository trackerRepository;

    public ReadingTrackerServiceImpl(TrackerRepository repository) { this.trackerRepository = repository; }

    @Override
    public BookDTO createNewBook(CreateBookRequest createBookRequest) {
        Book newBook = new Book();
        newBook.setName(createBookRequest.name());
        newBook.setAuthor(createBookRequest.author());
        newBook.setPages(createBookRequest.pages());

        return this.convertToDTO(this.trackerRepository.save(newBook));
    }

    @Override
    public List<BookDTO> getTrackedBooks() {
        return this.trackerRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getReadingStats() {
        return this.trackerRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public BookDTO updateTrackedProgress(long bookId, UpdateProgressRequest updateProgressRequest) {
        Optional<Book> existingBook = this.trackerRepository.findById(bookId);

        // No existing book, create it.
        if (!existingBook.isPresent()) {

        }

        Book book = existingBook.get();

        // If we got pages read, update that. Otherwise, find the pages read by multiplying total pages by the percent complete.
        if (updateProgressRequest.getPagesRead() != null) {
            book.setPages(updateProgressRequest.getPagesRead());
        } else {
            book.setPages((int) updateProgressRequest.getPercentComplete().doubleValue() * book.getPages());
        }

        return this.convertToDTO(this.trackerRepository.save(book));
    }

    private Book convertToModel(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.id());
        book.setName(bookDTO.name());
        book.setAuthor(bookDTO.author());
        book.setPages(bookDTO.pages());
        book.setPagesRead(bookDTO.pagesRead());

        return book;
    }

    private BookDTO convertToDTO(Book book) {
        return new BookDTO(book.getId(), book.getName(), book.getAuthor(), book.getPages(), book.getPagesRead());
    }
}

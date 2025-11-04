package com.codyperry.reading_tracker.service;

import com.codyperry.reading_tracker.dto.BookDTO;
import com.codyperry.reading_tracker.dto.CreateBookRequest;
import com.codyperry.reading_tracker.dto.UpdateProgressRequest;
import com.codyperry.reading_tracker.entity.Book;
import com.codyperry.reading_tracker.repository.TrackerRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReadingTrackerServiceTests {
    @Mock
    private TrackerRepository trackerRepository;

    @InjectMocks
    private ReadingTrackerServiceImpl trackerService;

    @Test
    @Order(1)
    void testCreateHappyPath() {
        CreateBookRequest request = new CreateBookRequest("Name", "Author", 200);
        BookDTO response = new BookDTO(1, request.name(), request.author(), request.pages(), 0);

        Book dbResponse = new Book();
        dbResponse.setPages(request.pages());
        dbResponse.setPagesRead(0);
        dbResponse.setAuthor(request.author());
        dbResponse.setName(request.name());
        dbResponse.setId(1L);

        Mockito.when(trackerRepository.save(Mockito.any(Book.class))).thenReturn(dbResponse);

        BookDTO savedBook = trackerService.createNewBook(request);

        assertEquals(response, savedBook);
    }

    @Test
    @Order(2)
    void testGetTrackedBooksEmpty() {
        Mockito.when(trackerRepository.findAll()).thenReturn(new ArrayList<>());

        List<BookDTO> trackedBooks = trackerService.getTrackedBooks();

        assertEquals(trackedBooks.isEmpty(), true);
    }

    @Test
    @Order(3)
    void testGetTrackedBooksHappyPath() {
        Book firstBook = new Book();
        firstBook.setPages(200);
        firstBook.setPagesRead(0);
        firstBook.setAuthor("First Author");
        firstBook.setName("First Name");
        firstBook.setId(1L);

        Book secondBook = new Book();
        secondBook.setPages(100);
        secondBook.setPagesRead(0);
        secondBook.setAuthor("Second Author");
        secondBook.setName("Second Name");
        secondBook.setId(2L);

        List<Book> books = new ArrayList<>();
        books.add(firstBook);
        books.add(secondBook);

        Mockito.when(trackerRepository.findAll()).thenReturn(books);

        List<BookDTO> trackedBooks = trackerService.getTrackedBooks();

        assertEquals(trackedBooks.isEmpty(), false);
        assertEquals(trackedBooks.size(), 2);
    }

    @Test
    @Order(4)
    void testGetStatsEmpty() {
        Mockito.when(trackerRepository.findAll()).thenReturn(new ArrayList<>());

        List<BookDTO> readingStats = trackerService.getReadingStats();

        assertEquals(readingStats.isEmpty(), true);
    }

    @Test
    @Order(5)
    void testGetStatsHappyPath() {
        Book firstBook = new Book();
        firstBook.setPages(200);
        firstBook.setPagesRead(0);
        firstBook.setAuthor("First Author");
        firstBook.setName("First Name");
        firstBook.setId(1L);

        Book secondBook = new Book();
        secondBook.setPages(100);
        secondBook.setPagesRead(0);
        secondBook.setAuthor("Second Author");
        secondBook.setName("Second Name");
        secondBook.setId(2L);

        List<Book> books = new ArrayList<>();
        books.add(firstBook);
        books.add(secondBook);

        Mockito.when(trackerRepository.findAll()).thenReturn(books);

        List<BookDTO> readingStats = trackerService.getReadingStats();

        assertEquals(readingStats.isEmpty(), false);
        assertEquals(readingStats.size(), 2);
    }

    @Test
    @Order(6)
    void testUpdateProgressNotFound() {
        UpdateProgressRequest request = new UpdateProgressRequest();
        request.setPagesRead(10);

        Mockito.when(trackerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());


        Optional<BookDTO> updatedProgress = trackerService.updateTrackedProgress(1, request);

        assertEquals(updatedProgress.isEmpty(), true);
    }

    @Test
    @Order(7)
    void testUpdateProgressPagesRead() {
        Book book = new Book();
        book.setPages(200);
        book.setPagesRead(0);
        book.setAuthor("First Author");
        book.setName("First Name");
        book.setId(1L);

        UpdateProgressRequest request = new UpdateProgressRequest();
        request.setPagesRead(10);

        Mockito.when(trackerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));

        book.setPagesRead(10);
        Mockito.when(trackerRepository.save(Mockito.any(Book.class))).thenReturn(book);


        Optional<BookDTO> updatedProgress = trackerService.updateTrackedProgress(1, request);

        assertEquals(updatedProgress.isEmpty(), false);
        assertEquals(10, updatedProgress.get().pagesRead());
    }

    @Test
    @Order(8)
    void testUpdateProgressPercentComplete() {
        Book book = new Book();
        book.setPages(200);
        book.setPagesRead(0);
        book.setAuthor("First Author");
        book.setName("First Name");
        book.setId(1L);

        UpdateProgressRequest request = new UpdateProgressRequest();
        request.setPercentComplete(0.1);

        Mockito.when(trackerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));

        book.setPagesRead(20);
        Mockito.when(trackerRepository.save(Mockito.any(Book.class))).thenReturn(book);


        Optional<BookDTO> updatedProgress = trackerService.updateTrackedProgress(1, request);

        assertEquals(updatedProgress.isEmpty(), false);
        assertEquals(20, updatedProgress.get().pagesRead());
        Mockito.verify(trackerRepository, Mockito.times(1)).save(book);
    }
}

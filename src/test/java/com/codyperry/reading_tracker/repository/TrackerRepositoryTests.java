package com.codyperry.reading_tracker.repository;

import com.codyperry.reading_tracker.entity.Book;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrackerRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TrackerRepository trackerRepository;

    @Test
    @Order(1)
    void testPersist() {
        Book newBook = new Book();
        newBook.setName("Name");
        newBook.setAuthor("Author");
        newBook.setPages(100);
        newBook.setPagesRead(0);

        Book savedBook = entityManager.persistAndFlush(newBook);
        assertEquals(1L, savedBook.getId());
        assertEquals("Name", savedBook.getName());
    }

    @Test
    @Order(2)
    void testPersistInvalidData() {
        Book newBook = new Book();
        newBook.setAuthor("Author");
        newBook.setPages(100);
        newBook.setPagesRead(0);

        assertThrows(ConstraintViolationException.class, () -> entityManager.persistAndFlush(newBook));
    }

    @Test
    @Order(3)
    void testFindAllEmpty() {
        List<Book> foundAll = trackerRepository.findAll();

        assertEquals(0, foundAll.size());
        assertEquals(true, foundAll.isEmpty());
    }

    @Test
    @Order(4)
    void testFindAll() {
        Book newBook = new Book();
        newBook.setName("Name");
        newBook.setAuthor("Author");
        newBook.setPages(100);
        newBook.setPagesRead(0);

        Book newBook2 = new Book();
        newBook2.setName("Name 2");
        newBook2.setAuthor("Author 2");
        newBook2.setPages(150);
        newBook2.setPagesRead(0);

        entityManager.persistAndFlush(newBook);
        entityManager.persistAndFlush(newBook2);

        List<Book> foundAll = trackerRepository.findAll();

        assertEquals(2, foundAll.size());
    }

    @Test
    @Order(5)
    void testFindByIdEmpty() {
        Optional<Book> found = trackerRepository.findById(1L);

        assertEquals(true, found.isEmpty());
    }

    @Test
    @Order(6)
    void testFindById() {
        Book newBook = new Book();
        newBook.setName("Name");
        newBook.setAuthor("Author");
        newBook.setPages(100);
        newBook.setPagesRead(0);

        Book savedBook = entityManager.persistAndFlush(newBook);

        Optional<Book> found = trackerRepository.findById(savedBook.getId());

        assertEquals(true, found.isPresent());
        assertEquals("Name", found.get().getName());
    }
}

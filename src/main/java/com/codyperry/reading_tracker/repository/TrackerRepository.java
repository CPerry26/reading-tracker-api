package com.codyperry.reading_tracker.repository;

import com.codyperry.reading_tracker.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackerRepository extends JpaRepository<Book, Long> {
}

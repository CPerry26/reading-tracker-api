package com.codyperry.reading_tracker.dto;

public record BookDTO(long id, String name, String author, int pages, int pagesRead) {
}

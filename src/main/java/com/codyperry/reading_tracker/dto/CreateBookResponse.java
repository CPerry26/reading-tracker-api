package com.codyperry.reading_tracker.dto;

public record CreateBookResponse(long id, String name, String author, int pages) {
}

package com.codyperry.reading_tracker.dto;

public record UpdateProgressResponse(long id, String name, String author, int percentComplete) {
}

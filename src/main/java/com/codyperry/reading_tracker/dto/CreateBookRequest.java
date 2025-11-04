package com.codyperry.reading_tracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateBookRequest(@NotNull String name, @NotNull String author, @Min(1) int pages) {
}

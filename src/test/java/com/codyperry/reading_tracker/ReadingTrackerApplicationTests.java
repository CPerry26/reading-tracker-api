package com.codyperry.reading_tracker;

import com.codyperry.reading_tracker.controller.ReadingTrackerController;
import com.codyperry.reading_tracker.repository.TrackerRepository;
import com.codyperry.reading_tracker.service.ReadingTrackerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReadingTrackerApplicationTests {

	@Autowired
	private ReadingTrackerController controller;

	@Autowired
	private ReadingTrackerService service;

	@Autowired
	private TrackerRepository repository;

	@Test
	void contextLoads() {
		// Basic smoke test to ensure everything is present on startup.
		assertThat(controller).isNotNull();
		assertThat(service).isNotNull();
		assertThat(repository).isNotNull();
	}

}

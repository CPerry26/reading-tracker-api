package com.codyperry.reading_tracker.controller;

import com.codyperry.reading_tracker.dto.BookDTO;
import com.codyperry.reading_tracker.dto.CreateBookRequest;
import com.codyperry.reading_tracker.dto.UpdateProgressRequest;
import com.codyperry.reading_tracker.service.ReadingTrackerService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ReadingTrackerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReadingTrackerControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReadingTrackerService trackerService;

    @Test
    @Order(1)
    void testCreateHappyPath() throws Exception {
        BookDTO mockBook = new BookDTO(1, "Name", "Author", 200, 0);
        String mockJson = "{\"name\":\"Name\",\"author\":\"Author\",\"pages\":200}";

        Mockito.when(trackerService.createNewBook(Mockito.any(CreateBookRequest.class))).thenReturn(mockBook);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books").accept(MediaType.APPLICATION_JSON)
                .content(mockJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(2)
    void testCreateInvalidData() throws Exception {
        String mockJson = "{\"author\":\"Author\",\"pages\":200}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books").accept(MediaType.APPLICATION_JSON)
                .content(mockJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        Mockito.verify(trackerService, Mockito.times(0)).createNewBook(Mockito.any());
    }

    @Test
    @Order(3)
    void testGetTrackedBooksEmpty() throws Exception {
        Mockito.when(trackerService.getTrackedBooks()).thenReturn(new ArrayList<>());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    @Order(4)
    void testGetTrackedBooksHappyPath() throws Exception {
        BookDTO mockBook = new BookDTO(1, "Name", "Author", 200, 0);
        List<BookDTO> trackedBooks = new ArrayList<>();
        trackedBooks.add(mockBook);

        String returnString = "[{\"id\":1,\"name\":\"Name\",\"author\":\"Author\",\"pages\":200,\"pagesRead\":0}]";

        Mockito.when(trackerService.getTrackedBooks()).thenReturn(trackedBooks);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(returnString, response.getContentAsString());
    }

    @Test
    @Order(5)
    void testGetReadingStatsEmpty() throws Exception {
        Mockito.when(trackerService.getReadingStats()).thenReturn(new ArrayList<>());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/stats").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    @Order(6)
    void testGetReadingStatsHappyPath() throws Exception {
        BookDTO mockBook = new BookDTO(1, "Name", "Author", 200, 0);
        List<BookDTO> bookStats = new ArrayList<>();
        bookStats.add(mockBook);

        String returnString = "[{\"id\":1,\"name\":\"Name\",\"author\":\"Author\",\"pages\":200,\"pagesRead\":0}]";

        Mockito.when(trackerService.getReadingStats()).thenReturn(bookStats);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/stats").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(returnString, response.getContentAsString());
    }

    @Test
    @Order(7)
    void testUpdateProgressUntracked() throws Exception {
        String mockJson = "{\"pagesRead\":10}";
        Mockito.when(trackerService.updateTrackedProgress(Mockito.anyLong(), Mockito.any(UpdateProgressRequest.class))).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/books/1").accept(MediaType.APPLICATION_JSON)
                .content(mockJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    @Order(8)
    void testUpdateProgressInvalidData() throws Exception {
        String mockJson = "{}";
        Mockito.when(trackerService.updateTrackedProgress(Mockito.anyLong(), Mockito.any(UpdateProgressRequest.class))).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/books/1").accept(MediaType.APPLICATION_JSON)
                .content(mockJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @Order(9)
    void testUpdateProgressInvalidId() throws Exception {
        String mockJson = "{\"pagesRead\":10}";
        Mockito.when(trackerService.updateTrackedProgress(Mockito.anyLong(), Mockito.any(UpdateProgressRequest.class))).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/books/asdf").accept(MediaType.APPLICATION_JSON)
                .content(mockJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @Order(9)
    void testUpdateProgressHappyPath() throws Exception {
        BookDTO mockBook = new BookDTO(1, "Name", "Author", 200, 20);
        String mockJson = "{\"pagesRead\":20}";
        String mockResponse = "{\"id\":1,\"name\":\"Name\",\"author\":\"Author\",\"percentComplete\":10}";

        Mockito.when(
                trackerService.updateTrackedProgress(
                        Mockito.anyLong(), Mockito.any(UpdateProgressRequest.class)
                )
        ).thenReturn(Optional.of(mockBook));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/books/1").accept(MediaType.APPLICATION_JSON)
                .content(mockJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(mockResponse, response.getContentAsString());
    }
}

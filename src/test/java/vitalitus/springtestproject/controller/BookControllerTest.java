package vitalitus.springtestproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import vitalitus.springtestproject.dto.BookDto;
import vitalitus.springtestproject.dto.CreateBookRequestDto;
import vitalitus.springtestproject.service.BookService;
import vitalitus.springtestproject.util.JwtUtil;
import vitalitus.springtestproject.util.TestDataHelper;

import java.math.BigDecimal;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserDetailsService userDetailsService;

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create book - returns created book")
    void createBook_ValidRequestDto_ReturnsBookDto() throws Exception {
        CreateBookRequestDto requestDto = TestDataHelper.createSpecificBookRequestDto(
            "Spring in Action", "Craig Walls", "978-1617294945", BigDecimal.valueOf(1200), List.of(1L)
        );

        BookDto expectedDto = TestDataHelper.createSpecificBookDto(1L, "Spring in Action", "Craig Walls");

        when(bookService.save(any(CreateBookRequestDto.class))).thenReturn(expectedDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/books").with(csrf())
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedDto.getId()))
                .andExpect(jsonPath("$.title").value(expectedDto.getTitle()))
                .andExpect(jsonPath("$.author").value(expectedDto.getAuthor()));
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get book by ID - returns book")
    void getBookById_ValidId_ReturnsBookDto() throws Exception {
        Long bookId = 1L;
        BookDto expectedDto = TestDataHelper.createSpecificBookDto(bookId, "Clean Code", "Robert C. Martin");

        when(bookService.getBookById(bookId)).thenReturn(expectedDto);

        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedDto.getId()))
                .andExpect(jsonPath("$.title").value(expectedDto.getTitle()))
                .andExpect(jsonPath("$.author").value(expectedDto.getAuthor()));
    }
}
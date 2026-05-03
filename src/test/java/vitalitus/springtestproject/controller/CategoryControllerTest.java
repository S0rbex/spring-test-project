package vitalitus.springtestproject.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import vitalitus.springtestproject.dto.CategoryDto;
import vitalitus.springtestproject.dto.CategoryRequestDto;
import vitalitus.springtestproject.service.CategoryService;
import vitalitus.springtestproject.service.BookService;
import vitalitus.springtestproject.util.JwtUtil;
import vitalitus.springtestproject.util.TestDataHelper;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private BookService bookService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserDetailsService userDetailsService;

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create category - returns created category")
    void createCategory_ValidRequestDto_ReturnsCategoryDto() throws Exception {
        CategoryRequestDto requestDto = TestDataHelper.createCategoryRequestDto();
        CategoryDto expectedDto = TestDataHelper.createCategoryDto();

        when(categoryService.save(any(CategoryRequestDto.class))).thenReturn(expectedDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/categories").with(csrf())
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedDto.getId()))
                .andExpect(jsonPath("$.name").value(expectedDto.getName()))
                .andExpect(jsonPath("$.description").value(expectedDto.getDescription()));
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get category by ID - returns category")
    void getCategoryById_ValidId_ReturnsCategoryDto() throws Exception {
        Long categoryId = 1L;
        CategoryDto expectedDto = TestDataHelper.createCategoryDto();

        when(categoryService.getById(categoryId)).thenReturn(expectedDto);

        mockMvc.perform(get("/categories/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedDto.getId()))
                .andExpect(jsonPath("$.name").value(expectedDto.getName()))
                .andExpect(jsonPath("$.description").value(expectedDto.getDescription()));
    }
}
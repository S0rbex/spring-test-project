package vitalitus.springtestproject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import vitalitus.springtestproject.dto.CategoryDto;
import vitalitus.springtestproject.dto.CategoryRequestDto;
import vitalitus.springtestproject.mapper.CategoryMapper;
import vitalitus.springtestproject.model.Category;
import vitalitus.springtestproject.repository.category.CategoryRepository;
import vitalitus.springtestproject.service.impl.CategoryServiceImpl;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Save category - valid input returns CategoryDto")
    void save_ValidCategoryRequest_ReturnsCategoryDto() {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Fiction");

        Category category = new Category();
        category.setName("Fiction");

        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Fiction");

        CategoryDto expectedDto = new CategoryDto();
        expectedDto.setId(1L);
        expectedDto.setName("Fiction");

        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(categoryMapper.toDto(savedCategory)).thenReturn(expectedDto);

        CategoryDto actualDto = categoryService.save(requestDto);

        assertEquals(expectedDto, actualDto);
        verify(categoryRepository).save(category);
    }

    @Test
    @DisplayName("Update category - valid input returns updated CategoryDto")
    void update_ValidInput_ReturnsUpdatedCategoryDto() {
        Long categoryId = 1L;
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Updated Fiction");

        Category category = new Category();
        category.setId(categoryId);
        category.setName("Old Fiction");

        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setName("Updated Fiction");

        CategoryDto expectedDto = new CategoryDto();
        expectedDto.setId(categoryId);
        expectedDto.setName("Updated Fiction");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(updatedCategory);
        when(categoryMapper.toDto(updatedCategory)).thenReturn(expectedDto);

        CategoryDto actualDto = categoryService.update(categoryId, requestDto);

        assertEquals(expectedDto, actualDto);
        verify(categoryMapper).updateCategoryFromDto(requestDto, category);
        verify(categoryRepository).save(category);
    }

    @Test
    @DisplayName("Get category by ID - valid ID returns CategoryDto")
    void getById_ValidId_ReturnsCategoryDto() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Science");

        CategoryDto expectedDto = new CategoryDto();
        expectedDto.setId(categoryId);
        expectedDto.setName("Science");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expectedDto);

        CategoryDto actualDto = categoryService.getById(categoryId);

        assertEquals(expectedDto, actualDto);
        verify(categoryRepository).findById(categoryId);
    }

    @Test
    @DisplayName("Delete category by ID")
    void deleteById_ValidId_DeletesCategory() {
        categoryService.deleteById(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Get all categories")
    void findAll_ValidPageable_ReturnsPageOfCategoryDtos() {
        Category category = new Category();
        category.setId(1L);
        category.setName("History");

        CategoryDto expectedDto = new CategoryDto();
        expectedDto.setId(1L);
        expectedDto.setName("History");

        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(List.of(category));

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(category)).thenReturn(expectedDto);

        Page<CategoryDto> actualPage = categoryService.findAll(pageable);

        assertEquals(1, actualPage.getTotalElements());
        assertEquals(expectedDto, actualPage.getContent().get(0));
        verify(categoryRepository).findAll(pageable);
    }
}
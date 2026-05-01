package vitalitus.springtestproject.service;

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
import vitalitus.springtestproject.util.TestDataHelper;

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
        CategoryRequestDto requestDto = TestDataHelper.createCategoryRequestDto();
        requestDto.setName("Fiction");
        requestDto.setDescription(null);

        Category category = TestDataHelper.createCategory("Fiction", null);

        Category savedCategory = TestDataHelper.createCategory("Fiction", null);
        savedCategory.setId(1L);

        CategoryDto expectedDto = TestDataHelper.createCategoryDto();
        expectedDto.setId(1L);
        expectedDto.setName("Fiction");
        expectedDto.setDescription(null);

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
        CategoryRequestDto requestDto = TestDataHelper.createCategoryRequestDto();
        requestDto.setName("Updated Fiction");
        requestDto.setDescription(null);

        Category category = TestDataHelper.createCategory("Old Fiction", null);
        category.setId(categoryId);

        Category updatedCategory = TestDataHelper.createCategory("Updated Fiction", null);
        updatedCategory.setId(categoryId);

        CategoryDto expectedDto = TestDataHelper.createCategoryDto();
        expectedDto.setId(categoryId);
        expectedDto.setName("Updated Fiction");
        expectedDto.setDescription(null);

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
        Category category = TestDataHelper.createCategory("Science", null);
        category.setId(categoryId);

        CategoryDto expectedDto = TestDataHelper.createCategoryDto();
        expectedDto.setId(categoryId);
        expectedDto.setName("Science");
        expectedDto.setDescription(null);

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
        Category category = TestDataHelper.createCategory("History", null);
        category.setId(1L);

        CategoryDto expectedDto = TestDataHelper.createCategoryDto();
        expectedDto.setId(1L);
        expectedDto.setName("History");
        expectedDto.setDescription(null);

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
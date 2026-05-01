package vitalitus.springtestproject.util;

import vitalitus.springtestproject.dto.BookDto;
import vitalitus.springtestproject.dto.CategoryDto;
import vitalitus.springtestproject.dto.CategoryRequestDto;
import vitalitus.springtestproject.dto.CreateBookRequestDto;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.model.Category;

import java.math.BigDecimal;
import java.util.List;

public class TestDataHelper {

    public static CategoryRequestDto createCategoryRequestDto() {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Science Fiction");
        requestDto.setDescription("Sci-fi books");
        return requestDto;
    }

    public static CategoryRequestDto createUpdatedCategoryRequestDto() {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Updated Fiction");
        requestDto.setDescription("Updated Description");
        return requestDto;
    }

    public static CategoryDto createCategoryDto() {
        CategoryDto expectedDto = new CategoryDto();
        expectedDto.setId(1L);
        expectedDto.setName("Science Fiction");
        expectedDto.setDescription("Sci-fi books");
        return expectedDto;
    }

    public static CreateBookRequestDto createBookRequestDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Integration Testing");
        requestDto.setAuthor("Test Author");
        requestDto.setIsbn("978-3-16-148410-0");
        requestDto.setPrice(BigDecimal.valueOf(1500));
        requestDto.setCategoryIds(List.of(1L));
        return requestDto;
    }
    
    public static CreateBookRequestDto createSpecificBookRequestDto(String title, String author, String isbn, BigDecimal price, List<Long> categoryIds) {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle(title);
        requestDto.setAuthor(author);
        requestDto.setIsbn(isbn);
        requestDto.setPrice(price);
        requestDto.setCategoryIds(categoryIds);
        return requestDto;
    }

    public static CreateBookRequestDto createUpdatedBookRequestDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Updated Title");
        requestDto.setAuthor("Updated Author");
        requestDto.setIsbn("123-4-56-789012-3");
        requestDto.setPrice(BigDecimal.valueOf(2000));
        requestDto.setCategoryIds(List.of(1L));
        return requestDto;
    }
    
    public static BookDto createBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Integration Testing");
        bookDto.setAuthor("Test Author");
        return bookDto;
    }
    
    public static BookDto createSpecificBookDto(Long id, String title, String author) {
        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setTitle(title);
        bookDto.setAuthor(author);
        return bookDto;
    }

    public static Category createCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return category;
    }

    public static Book createBook(String title, String author, String isbn, BigDecimal price, Category category) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPrice(price);
        if (category != null) {
            book.getCategories().add(category);
        }
        return book;
    }
}
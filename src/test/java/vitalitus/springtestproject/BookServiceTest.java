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
import vitalitus.springtestproject.dto.BookDto;
import vitalitus.springtestproject.dto.CreateBookRequestDto;
import vitalitus.springtestproject.mapper.BookMapper;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.repository.book.BookRepository;
import vitalitus.springtestproject.repository.category.CategoryRepository;
import vitalitus.springtestproject.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Get book by ID - valid ID returns BookDto")
    void getBookById_ValidId_ReturnsBookDto() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPrice(BigDecimal.valueOf(100));

        BookDto expectedDto = new BookDto();
        expectedDto.setId(bookId);
        expectedDto.setTitle("Test Book");
        expectedDto.setAuthor("Test Author");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        BookDto actualDto = bookService.getBookById(bookId);

        assertEquals(expectedDto, actualDto);
        verify(bookRepository).findById(bookId);
    }

    @Test
    @DisplayName("Save book - valid input returns BookDto")
    void save_ValidCreateBookRequest_ReturnsBookDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("New Book");
        requestDto.setAuthor("Author");
        requestDto.setPrice(BigDecimal.valueOf(200));

        Book book = new Book();
        book.setTitle("New Book");
        book.setAuthor("Author");
        book.setPrice(BigDecimal.valueOf(200));

        BookDto expectedDto = new BookDto();
        expectedDto.setId(1L);
        expectedDto.setTitle("New Book");
        expectedDto.setAuthor("Author");

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        BookDto actualDto = bookService.save(requestDto);

        assertEquals(expectedDto, actualDto);
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("Update book - valid input returns updated BookDto")
    void updateBook_ValidInput_ReturnsUpdatedBookDto() {
        Long bookId = 1L;
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Updated Book");
        requestDto.setAuthor("Updated Author");
        requestDto.setPrice(BigDecimal.valueOf(300));

        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Old Book");
        book.setAuthor("Old Author");

        Book updatedBook = new Book();
        updatedBook.setId(bookId);
        updatedBook.setTitle("Updated Book");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setPrice(BigDecimal.valueOf(300));

        BookDto expectedDto = new BookDto();
        expectedDto.setId(bookId);
        expectedDto.setTitle("Updated Book");
        expectedDto.setAuthor("Updated Author");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(expectedDto);

        BookDto actualDto = bookService.updateBook(bookId, requestDto);

        assertEquals(expectedDto, actualDto);
        verify(bookMapper).updateBookFromDto(requestDto, book);
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("Delete book by ID")
    void delete_ValidId_DeletesBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        
        bookService.delete(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Get all books")
    void findAll_ValidPageable_ReturnsPageOfBookDtos() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");

        BookDto expectedDto = new BookDto();
        expectedDto.setId(1L);
        expectedDto.setTitle("Clean Code");

        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(List.of(book));

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        Page<BookDto> actualPage = bookService.findAll(pageable);

        assertEquals(1, actualPage.getTotalElements());
        assertEquals(expectedDto, actualPage.getContent().get(0));
        verify(bookRepository).findAll(pageable);
    }
}
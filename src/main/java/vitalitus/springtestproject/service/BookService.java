package vitalitus.springtestproject.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vitalitus.springtestproject.dto.BookDto;
import vitalitus.springtestproject.dto.BookDtoWithoutCategoryIds;
import vitalitus.springtestproject.dto.CreateBookRequestDto;
import vitalitus.springtestproject.repository.book.BookSearchParameters;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    Page<BookDto> findAll(Pageable pageable);

    BookDto getBookById(Long id);

    void delete(Long id);

    BookDto updateBook(Long id, CreateBookRequestDto createBookRequestDto);

    Page<BookDto> search(BookSearchParameters params, Pageable page);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id);
}

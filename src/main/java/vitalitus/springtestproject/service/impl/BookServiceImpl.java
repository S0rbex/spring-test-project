package vitalitus.springtestproject.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vitalitus.springtestproject.dto.BookDto;
import vitalitus.springtestproject.dto.BookDtoWithoutCategoryIds;
import vitalitus.springtestproject.dto.CreateBookRequestDto;
import vitalitus.springtestproject.exception.EntityNotFoundException;
import vitalitus.springtestproject.mapper.BookMapper;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.model.Category;
import vitalitus.springtestproject.repository.book.BookRepository;
import vitalitus.springtestproject.repository.book.BookSearchParameters;
import vitalitus.springtestproject.repository.book.BookSpecificationBuilder;
import vitalitus.springtestproject.repository.category.CategoryRepository;
import vitalitus.springtestproject.service.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        setCategoriesToBook(book, requestDto.getCategoryIds());
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toDto);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't delete book: no book with id " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto updateBook(Long id, CreateBookRequestDto createBookRequestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find book with this id: " + id));
        bookMapper.updateBookFromDto(createBookRequestDto, book);
        setCategoriesToBook(book, createBookRequestDto.getCategoryIds());
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public Page<BookDto> search(BookSearchParameters params, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);

        return bookRepository.findAll(bookSpecification, pageable).map(bookMapper::toDto);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id) {
        return bookRepository.findAllByCategoriesId(id).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .collect(Collectors.toList());
    }

    private void setCategoriesToBook(Book book, List<Long> categoryIds) {
        if (categoryIds != null && !categoryIds.isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(categoryIds);
            if (categories.size() != categoryIds.size()) {
                throw new EntityNotFoundException("One or more categories "
                        + "not found by provided IDs");
            }
            book.setCategories(new HashSet<>(categories));
        } else {
            book.setCategories(Collections.emptySet());
        }
    }
}

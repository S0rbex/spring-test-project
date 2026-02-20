package vitalitus.springtestproject.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vitalitus.springtestproject.dto.BookDto;
import vitalitus.springtestproject.dto.CreateBookRequestDto;
import vitalitus.springtestproject.exception.EntityNotFoundException;
import vitalitus.springtestproject.mapper.BookMapper;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.repository.BookRepository;
import vitalitus.springtestproject.service.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll()
        .stream()
        .map(bookMapper::toDto)
        .toList();
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
        return bookMapper.toDto(bookRepository.save(book));
    }
}

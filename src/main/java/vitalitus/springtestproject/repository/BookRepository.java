package vitalitus.springtestproject.repository;

import java.util.List;
import vitalitus.springtestproject.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();

    Book getBookById(Long id);
}

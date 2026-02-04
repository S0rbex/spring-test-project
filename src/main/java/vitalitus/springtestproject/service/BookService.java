package vitalitus.springtestproject.service;

import java.util.List;
import vitalitus.springtestproject.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}

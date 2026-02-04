package vitalitus.springtestproject.service;

import org.springframework.stereotype.Service;
import vitalitus.springtestproject.model.Book;

import java.util.List;

@Service
public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}

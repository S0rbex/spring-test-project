package vitalitus.springtestproject.repository;

import org.springframework.stereotype.Repository;
import vitalitus.springtestproject.model.Book;
import java.util.List;

@Repository
public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}

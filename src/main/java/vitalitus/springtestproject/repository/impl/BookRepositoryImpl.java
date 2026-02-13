package vitalitus.springtestproject.repository.impl;

import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vitalitus.springtestproject.exception.DataProcessingException;
import vitalitus.springtestproject.exception.EntityNotFoundException;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.repository.BookRepository;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public BookRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction tr = null;
        try {
            session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
            tr = session.beginTransaction();
            session.persist(book);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            throw new DataProcessingException("Can`t save " + book + "  in DB");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        try (var session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return session.createQuery("from Book b", Book.class).getResultList();
        } catch (Exception e) {
            throw new EntityNotFoundException("Can`t get all books");
        }
    }

    @Override
    public Book getBookById(Long id) {
        try (var session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return session.get(Book.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get book by this id:" + id);
        }
    }
}

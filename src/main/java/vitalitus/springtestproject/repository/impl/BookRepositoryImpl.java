package vitalitus.springtestproject.repository.impl;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vitalitus.springtestproject.exception.DataProcessingException;
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
            CriteriaQuery<Book> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Book.class);
            criteriaQuery.from(Book.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get all books");
        }
    }
}

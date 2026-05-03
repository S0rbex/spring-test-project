package vitalitus.springtestproject.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.model.Category;
import vitalitus.springtestproject.repository.book.BookRepository;
import vitalitus.springtestproject.repository.category.CategoryRepository;
import vitalitus.springtestproject.util.TestDataHelper;

import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Find all books by existing category ID")
    void findAllByCategoriesId_ExistingCategoryId_ReturnsBooks() {
        Category category = TestDataHelper.createCategory("Fantasy", null);
        Category savedCategory = categoryRepository.save(category);

        Book book1 = TestDataHelper.createBook("Harry Potter", "J.K. Rowling", "1234567890", BigDecimal.valueOf(500), savedCategory);
        bookRepository.save(book1);

        Book book2 = TestDataHelper.createBook("Lord of the Rings", "J.R.R. Tolkien", "0987654321", BigDecimal.valueOf(600), savedCategory);
        bookRepository.save(book2);

        Book book3 = TestDataHelper.createBook("Java Concurrency in Practice", "Brian Goetz", "1111111111", BigDecimal.valueOf(800), null);
        bookRepository.save(book3);

        List<Book> actualBooks = bookRepository.findAllByCategoriesId(savedCategory.getId());

        assertEquals(2, actualBooks.size());
        boolean containsBook1 = actualBooks.stream().anyMatch(b -> b.getTitle().equals("Harry Potter"));
        boolean containsBook2 = actualBooks.stream().anyMatch(b -> b.getTitle().equals("Lord of the Rings"));

        assertTrue(containsBook1);
        assertTrue(containsBook2);
    }

    @Test
    @DisplayName("Find all books by non-existing category ID returns empty list")
    void findAllByCategoriesId_NonExistingCategoryId_ReturnsEmptyList() {
        List<Book> actualBooks = bookRepository.findAllByCategoriesId(999L);

        assertTrue(actualBooks.isEmpty());
    }
}
package vitalitus.springtestproject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.model.Category;
import vitalitus.springtestproject.repository.book.BookRepository;
import vitalitus.springtestproject.repository.category.CategoryRepository;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Find all books by existing category ID")
    void findAllByCategoriesId_ExistingCategoryId_ReturnsBooks() {
        Category category = new Category();
        category.setName("Fantasy");
        Category savedCategory = categoryRepository.save(category);

        Book book1 = new Book();
        book1.setTitle("Harry Potter");
        book1.setAuthor("J.K. Rowling");
        book1.setIsbn("1234567890");
        book1.setPrice(BigDecimal.valueOf(500));
        book1.getCategories().add(savedCategory);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Lord of the Rings");
        book2.setAuthor("J.R.R. Tolkien");
        book2.setIsbn("0987654321");
        book2.setPrice(BigDecimal.valueOf(600));
        book2.getCategories().add(savedCategory);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("Java Concurrency in Practice");
        book3.setAuthor("Brian Goetz");
        book3.setIsbn("1111111111");
        book3.setPrice(BigDecimal.valueOf(800));
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
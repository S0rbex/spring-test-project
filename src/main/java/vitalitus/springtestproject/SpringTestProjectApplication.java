package vitalitus.springtestproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.repository.BookRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class SpringTestProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTestProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookRepository repository) {
        return args -> {
            Book book = new Book();
            book.setTitle("Clean Code");
            book.setAuthor("Robert C. Martin");
            book.setIsbn("978-0132350884");
            book.setPrice(new BigDecimal("45.50"));
            repository.save(book);

            System.out.println("Книга успішно збережена в БД!");
        };
    }

}

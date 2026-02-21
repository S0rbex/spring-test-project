package vitalitus.springtestproject.repository.book.specification;

import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.repository.SpecificationProvider;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    private static final String KEY = "title";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get(KEY).in(Arrays.stream(params).toArray());
    }
}

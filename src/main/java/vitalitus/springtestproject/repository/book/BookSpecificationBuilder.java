package vitalitus.springtestproject.repository.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.repository.SpecificationBuilder;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book, BookSearchParameters> {
    private static final String AUTHOR_KEY = "author";
    private static final String TITLE_KEY = "title";

    private final BookSpecificationProviderManager bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.title() != null && searchParameters.title().length > 0) {
            spec = bookSpecificationProviderManager.getSpecificationProvider(TITLE_KEY)
                    .getSpecification(searchParameters.title());
        }
        if (searchParameters.author() != null && searchParameters.author().length > 0) {
            spec = bookSpecificationProviderManager.getSpecificationProvider(AUTHOR_KEY)
                    .getSpecification(searchParameters.author());
        }
        return spec;
    }
}

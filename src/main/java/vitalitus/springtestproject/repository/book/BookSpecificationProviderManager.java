package vitalitus.springtestproject.repository.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.repository.SpecificationProvider;
import vitalitus.springtestproject.repository.SpecificationProviderManager;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders
                .stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(
                    () -> new RuntimeException(
                            "Can`t find correct specification with key: " + key));
    }
}

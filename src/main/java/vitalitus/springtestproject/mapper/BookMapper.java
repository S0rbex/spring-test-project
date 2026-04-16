package vitalitus.springtestproject.mapper;

import java.util.Optional;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import vitalitus.springtestproject.config.MapperConfig;
import vitalitus.springtestproject.dto.BookDto;
import vitalitus.springtestproject.dto.BookDtoWithoutCategoryIds;
import vitalitus.springtestproject.dto.CreateBookRequestDto;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.model.Category;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto createBookRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateBookFromDto(CreateBookRequestDto dto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            bookDto.setCategoryIds(book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toList()));
        }
    }

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        return Optional.ofNullable(id)
                .map(bookId -> {
                    Book book = new Book();
                    book.setId(bookId);
                    return book;
                })
                .orElse(null);
    }
}

package vitalitus.springtestproject.mapper;

import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vitalitus.springtestproject.config.MapperConfig;
import vitalitus.springtestproject.dto.BookDto;
import vitalitus.springtestproject.dto.BookDtoWithoutCategoryIds;
import vitalitus.springtestproject.dto.CreateBookRequestDto;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.model.Category;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto createBookRequestDto);

    @Mapping(target = "id", ignore = true)
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

    @AfterMapping
    default void setCategories(@MappingTarget Book book, CreateBookRequestDto bookDto) {
        if (bookDto.getCategoryIds() != null) {
            book.setCategories(bookDto.getCategoryIds().stream()
                    .map(Category::new)
                    .collect(Collectors.toSet()));
        }
    }

}

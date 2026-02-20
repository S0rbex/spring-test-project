package vitalitus.springtestproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vitalitus.springtestproject.config.MapperConfig;
import vitalitus.springtestproject.dto.BookDto;
import vitalitus.springtestproject.dto.CreateBookRequestDto;
import vitalitus.springtestproject.model.Book;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto createBookRequestDto);

    @Mapping(target = "id", ignore = true)
    void updateBookFromDto(CreateBookRequestDto dto, @MappingTarget Book book);
}

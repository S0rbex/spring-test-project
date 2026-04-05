package vitalitus.springtestproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vitalitus.springtestproject.config.MapperConfig;
import vitalitus.springtestproject.dto.CategoryDto;
import vitalitus.springtestproject.dto.CategoryRequestDto;
import vitalitus.springtestproject.model.Category;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CategoryRequestDto categoryRequestDto);

    @Mapping(target = "id", ignore = true)
    void updateCategoryFromDto(CategoryRequestDto dto, @MappingTarget Category category);
}

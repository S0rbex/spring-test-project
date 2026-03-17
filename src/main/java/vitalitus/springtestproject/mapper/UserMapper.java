package vitalitus.springtestproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vitalitus.springtestproject.config.MapperConfig;
import vitalitus.springtestproject.dto.CreateUserRequestDto;
import vitalitus.springtestproject.dto.UserDto;
import vitalitus.springtestproject.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);

    User toModel(CreateUserRequestDto createUserRequestDto);

    @Mapping(target = "id", ignore = true)
    void updateUserFromDto(CreateUserRequestDto createUserRequestDto, @MappingTarget User user);
}

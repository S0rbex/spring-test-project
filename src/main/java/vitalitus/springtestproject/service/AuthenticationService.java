package vitalitus.springtestproject.service;

import vitalitus.springtestproject.dto.CreateUserRequestDto;
import vitalitus.springtestproject.dto.UserDto;

public interface AuthenticationService {
    UserDto registrationUser(CreateUserRequestDto userRequestDto);
}

package vitalitus.springtestproject.service;

import vitalitus.springtestproject.dto.CreateUserRequestDto;
import vitalitus.springtestproject.dto.UserDto;
import vitalitus.springtestproject.dto.UserLoginRequestDto;
import vitalitus.springtestproject.dto.UserLoginResponseDto;

public interface AuthenticationService {
    UserDto registrationUser(CreateUserRequestDto userRequestDto);

    UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto);
}

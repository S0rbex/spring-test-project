package vitalitus.springtestproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vitalitus.springtestproject.dto.CreateUserRequestDto;
import vitalitus.springtestproject.dto.UserDto;
import vitalitus.springtestproject.exception.RegistrationException;
import vitalitus.springtestproject.mapper.UserMapper;
import vitalitus.springtestproject.model.User;
import vitalitus.springtestproject.repository.user.UserRepository;
import vitalitus.springtestproject.service.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto registrationUser(CreateUserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new RegistrationException("This email is already registered, try to login");
        }
        User user = userMapper.toModel(userRequestDto);
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}

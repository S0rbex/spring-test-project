package vitalitus.springtestproject.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vitalitus.springtestproject.dto.CreateUserRequestDto;
import vitalitus.springtestproject.dto.UserDto;
import vitalitus.springtestproject.exception.RegistrationException;
import vitalitus.springtestproject.service.AuthenticationService;

@Tag(name = "User authentication", description = "Endpoints for authentication user")
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public UserDto register(
            @RequestBody @Valid CreateUserRequestDto request) throws RegistrationException {

        return authenticationService.registrationUser(request);
    }
}

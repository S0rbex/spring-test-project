package vitalitus.springtestproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vitalitus.springtestproject.dto.CreateUserRequestDto;
import vitalitus.springtestproject.dto.UserDto;
import vitalitus.springtestproject.dto.UserLoginRequestDto;
import vitalitus.springtestproject.dto.UserLoginResponseDto;
import vitalitus.springtestproject.exception.RegistrationException;
import vitalitus.springtestproject.service.AuthenticationService;

@Tag(name = "User authentication", description = "Endpoints for authentication user")
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication",
        description = "Endpoint for managing authentication, registration and login")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account based on the provided registration details."
    )
    @ApiResponse(responseCode = "200", description = "User successfully registered")
    @PostMapping("/registration")
    public UserDto register(
            @RequestBody @Valid CreateUserRequestDto request) throws RegistrationException {

        return authenticationService.registrationUser(request);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid
                                           UserLoginRequestDto requestDto) {
        return authenticationService.loginUser(requestDto);
    }
}

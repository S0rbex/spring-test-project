package vitalitus.springtestproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import vitalitus.springtestproject.validation.FieldMatch;

@Getter
@Setter
@FieldMatch(first = "password", second = "repeatPassword")
public class CreateUserRequestDto {
    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    @NotBlank
    @Size(min = 8, max = 50)
    private String repeatPassword;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    private String shippingAddress;
}

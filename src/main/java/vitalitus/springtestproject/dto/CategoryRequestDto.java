package vitalitus.springtestproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryRequestDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String description;
}

package vitalitus.springtestproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String title;
    @NotBlank
    @Column(nullable = false)
    private String author;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String isbn;
    @NotNull
    @Column(nullable = false)
    private BigDecimal price;
    private String description;
    private String coverImage;
}

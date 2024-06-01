package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Author is mandatory")
    private String author;

    @Pattern(regexp = "\\d{4}", message = "Publication year must be a four-digit number")
    private String publicationYear;

    @NotBlank(message = "ISBN is mandatory")
    private String isbn;
}

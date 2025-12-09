package com.example.library_management.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {

    //todo : post only necessary data to server (entity)
    private Integer authorId;
    private Integer categoryId;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 4, max = 30, message = "Title must be between 4 to 30 characters")
    private String title;

    @Positive (message = "Author Id must positive number")
    @NotNull(message = "Author ID is required")
    @JsonProperty("authorId")
    public Integer getAuthorId() { return authorId; }

    @Positive (message = "Category Id must positive number")
    @NotNull(message = "Category ID is required")
    @JsonProperty("categoryId")
    public Integer getCategoryId() { return categoryId; }

    @NotNull(message = "publish date can't not null")
    private LocalDateTime publishDate;
}

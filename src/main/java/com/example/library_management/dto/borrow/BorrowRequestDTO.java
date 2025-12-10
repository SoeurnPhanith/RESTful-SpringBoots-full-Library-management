package com.example.library_management.dto.borrow;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BorrowRequestDTO {

    @JsonProperty("userId")
    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    private Integer user;

    @JsonProperty("bookId")
    @NotNull(message = "Book ID is required")
    @Positive(message = "Book ID must be positive")
    private Integer book;

    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate; //dak dai song
}
